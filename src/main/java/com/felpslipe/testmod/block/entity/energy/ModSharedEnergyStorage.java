package com.felpslipe.testmod.block.entity.energy;

import com.felpslipe.testmod.block.entity.CableBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnknownNullability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModSharedEnergyStorage<TBlockEntity extends BlockEntity> extends ModEnergyStorage {
    private static final Logger LOG = LoggerFactory.getLogger(CableBlockEntity.class);
    private final ArrayList<TBlockEntity> blockEntities;
    private final int energyPerCable;

    /**
     * @param blockEntity The block entity to be added to the shared energy storage.
     * @param energyPerCable The amount of energy per cable.
     * @param maxTransfer The maximum transfer rate.
     */
    public ModSharedEnergyStorage(TBlockEntity blockEntity, int energyPerCable, int maxTransfer) {
        super(energyPerCable, maxTransfer);
        this.energyPerCable = energyPerCable;
        ArrayList<TBlockEntity> blockEntities = new ArrayList<>();
        blockEntities.add(blockEntity);
        this.blockEntities = blockEntities;
    }

    /**
     * @param blockEntities The list of block entities to be added to the shared energy storage.
     * @param energyPerCable The amount of energy per cable.
     * @param maxTransfer The maximum transfer rate.
     */
    public ModSharedEnergyStorage(Collection<TBlockEntity> blockEntities, int energyPerCable, int maxTransfer) {
        super(energyPerCable, maxTransfer);
        this.energyPerCable = energyPerCable;
        this.blockEntities = new ArrayList<>();
        addBlockEntity(blockEntities);
    }

    /**
     * @param blockEntities The list of block entities to be added to the shared energy storage.
     */
    public void addBlockEntity(Collection<TBlockEntity> blockEntities) {
        blockEntities.forEach(this::addBlockEntityInternal);
        updateCapacity();
    }

    /**
     * @param blockEntity The block entity to be added to the shared energy storage.
     */
    public void addBlockEntity(@UnknownNullability TBlockEntity blockEntity) {
        addBlockEntityInternal(blockEntity);
        updateCapacity();
    }

    /**
     * Adds a block entity to the shared energy storage.
     * @param blockEntity The block entity to be added.
     */
    protected void addBlockEntityInternal(@UnknownNullability TBlockEntity blockEntity) {
        if (blockEntity == null) return;

        if (blockEntity.isRemoved()) {
            throw new IllegalStateException("Block entity is removed");
        }

        if (!blockEntities.contains(blockEntity)) {
            blockEntities.add(blockEntity);
        }
    }

    /**
     * Clears all block entities from the shared energy storage.
     */
    public void clearBlockEntitles() {
        blockEntities.clear();
        updateCapacity();
    }

    /**
     * Removes all block entities from the shared energy storage.
     * @param blockEntities The collection of block entities to be removed.
     */
    public void removeAllBlockEntities(Collection<TBlockEntity> blockEntities) {
        this.blockEntities.removeAll(blockEntities);
        updateCapacity();
    }

    /**
     * Removes a block entity from the shared energy storage.
     * @param blockEntity The block entity to be removed.
     */
    public void removeBlockEntity(TBlockEntity blockEntity) {
        blockEntities.remove(blockEntity);
        updateCapacity();
    }

    /**
     * Updates the capacity of the shared energy storage based on the number of connected block entities.
     */
    private void updateCapacity() {
        capacity = energyPerCable * blockEntities.size();
        energy = Math.min(energy, capacity);
    }

    /**
     * Serializes the energy storage to NBT.
     * @param blockEntity The block entity to be serialized.
     * @param registries The holder lookup provider.
     * @return The serialized NBT data.
     */
    public Tag serializeNBT(TBlockEntity blockEntity, HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        if (blockEntities.contains(blockEntity)) {
            tag.put("network.energy", super.serializeNBT(registries));
            ListTag blockEntitiesLoc = new ListTag();
            for (TBlockEntity block : blockEntities) {
                BlockPos.CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, block.getBlockPos()).result()
                        .ifPresent(pos -> {
                            CompoundTag blockTag = new CompoundTag();
                            blockTag.put("pos", pos);
                            blockEntitiesLoc.add(blockTag);
                        });
            }
            tag.put("network.blockEntities", blockEntitiesLoc);
        }
        return tag;
    }

    @Deprecated
    @Override
    public Tag serializeNBT(HolderLookup.Provider registries) {
        throw new UnsupportedOperationException("Use serializeNBT(TBlockEntity blockEntity, HolderLookup.Provider registries) instead");
    }

    @Contract(pure = true)
    public static <TBlockEntity extends BlockEntity> ModSharedEnergyStorage<TBlockEntity> deserializeNBT(
            TBlockEntity blockEntity, int energyPerCable, int maxTransfer, HolderLookup.Provider registries, Tag nbt) {
        ModSharedEnergyStorage<TBlockEntity> storage = new ModSharedEnergyStorage<>(blockEntity, energyPerCable, maxTransfer);
        storage.deserializeNBT(blockEntity, registries, nbt);
        return storage;
    }

    /**
     * Deserializes the energy storage from NBT.
     * @param blockEntity The block entity to be deserialized.
     * @param registries The holder lookup provider.
     * @param nbt The NBT data to deserialize from.
     */
    public void deserializeNBT(TBlockEntity blockEntity, HolderLookup.Provider registries, Tag nbt) {
        if (blockEntities.contains(blockEntity)) {
            if (nbt instanceof CompoundTag tag) {
                if (tag.contains("network.energy")) {
                    Tag netEnergy = tag.get("network.energy");
                    if (netEnergy != null) {
                        super.deserializeNBT(registries, netEnergy);
                    }
                }
                if (tag.contains("network.blockEntities")) {
                    ListTag blockEntitiesLoc = tag.getList("network.blockEntities", 10);
                    for (int i = 0; i < blockEntitiesLoc.size(); i++) {
                        CompoundTag blockTag = blockEntitiesLoc.getCompound(i);
                        BlockPos pos = BlockPos.CODEC.parse(net.minecraft.nbt.NbtOps.INSTANCE, blockTag.get("pos")).result().orElse(null);
                        if (blockEntity.getLevel() != null && pos != null) {
                            if (blockEntities.stream().noneMatch(block -> block.getBlockPos().equals(pos))) {
                                BlockEntity posBlockEntity = blockEntity.getLevel().getBlockEntity(pos);
                                if (posBlockEntity != null) {
                                    //noinspection unchecked
                                    addBlockEntity((TBlockEntity) posBlockEntity);
                                } else {
                                    LOG.warn("BlockEntity is was not found at pos {} on level {}", pos, blockEntity.getLevel());
                                }
                            } else {
                                LOG.info("Block entity already added: {}", blockEntity);
                            }
                        } else {
                            LOG.warn("Block entity level or position is null: pos {} on level {}", pos, blockEntity.getLevel());
                        }
                    }
                } else {
                    LOG.warn("NBT does not contain block entities: {}", nbt);
                }
            } else {
                LOG.warn("NBT is not a CompoundTag: {}", nbt);
            }
        } else {
            LOG.info("Block entity is not part of the network: {}", blockEntity);
        }
    }

    @Deprecated
    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        throw new UnsupportedOperationException("Use deserializeNBT(TBlockEntity blockEntity, HolderLookup.Provider provider, Tag nbt) instead");
    }

    /**
     * Called when the energy storage changes.
     */
    @Override
    public void onEnergyChanged() {
        for (BlockEntity blockEntity : blockEntities) {
            if (blockEntity != null) {
                blockEntity.setChanged();
//                Level level = blockEntity.getLevel();
//                if (level != null) {
//                    BlockState blockState = blockEntity.getBlockState();
//                    level.sendBlockUpdated(blockEntity.getBlockPos(), blockState, blockState, 3);
//                }
            }
        }
    }

    /**
     * Gets the connected block entities.
     * @return The collection of connected block entities.
     */
    public Collection<TBlockEntity> getConnectedBlockEntities() {
        return Collections.unmodifiableCollection(blockEntities);
    }

    @Override
    public String toString() {
        return "ModSharedEnergyStorage{" +
                "hash=" + Integer.toHexString(hashCode()) +
                ", energyPerCable=" + energyPerCable +
                ", energy=" + energy +
                ", capacity=" + capacity +
                ", maxTransfer=" + maxExtract +
                ", blockEntities=" + blockEntities.stream()
                .map(BlockEntity::getBlockPos).map(BlockPos::toString)
                .collect(Collectors.joining(";", "{", "}")) +
                '}';
    }
}
