package com.felpslipe.testmod.block.entity;

import com.felpslipe.testmod.block.entity.energy.ModEnergyUtil;
import com.felpslipe.testmod.block.entity.energy.ModSharedEnergyStorage;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CableBlockEntity extends BlockEntity {

    private static final int ENERGY_TRANSFER_AMOUNT = 480;
    private static final int ENERGY_STORAGE_AMOUNT_PER_CABLE = 6400;
    private static final HashSet<BlockPos> stillToUpdate = new HashSet<>();

    private ModSharedEnergyStorage<CableBlockEntity> ENERGY_STORAGE;
    private final Logger LOG = LoggerFactory.getLogger(CableBlockEntity.class);

    public CableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CABLE_BE.get(), pos, blockState);
    }

    @Override
    public void onLoad() {
        if (level != null && !level.isClientSide && ENERGY_STORAGE == null) {
            updateEnergyStorage();
        }
        super.onLoad();
    }

    private void updateEnergyStorage() {
        if (level == null || level.isClientSide) {
            return;
        }

        // Find all cables that are physically connected using flood fill
        HashSet<CableBlockEntity> connectedCables = new HashSet<>();
        collectConnectedCables(this, connectedCables);

        // If we're alone, create single cable network
        if (connectedCables.size() == 1) {
            // Get current energy if we have any
            int currentEnergy = ENERGY_STORAGE != null ? ENERGY_STORAGE.getEnergyStored() : 0;

            ENERGY_STORAGE = new ModSharedEnergyStorage<>(connectedCables,
                    ENERGY_STORAGE_AMOUNT_PER_CABLE,
                    ENERGY_TRANSFER_AMOUNT);
            ENERGY_STORAGE.setEnergy(currentEnergy);
            connectedCables.stream().map(BlockEntity::getBlockPos).toList().forEach(stillToUpdate::remove);
            return;
        }

        // Find an existing network we can use
        ModSharedEnergyStorage<CableBlockEntity> networkToUse = connectedCables.stream()
                .map(cable -> cable.ENERGY_STORAGE)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(n -> n.getConnectedBlockEntities().size()))
                .orElse(null);
        HashSet<ModSharedEnergyStorage<CableBlockEntity>> oldNetworks = new HashSet<>();
        int totalEnergy = networkToUse != null ? networkToUse.getEnergyStored() : 0;

        if (networkToUse != null) {
            for (CableBlockEntity cable : connectedCables) {
                if (cable.ENERGY_STORAGE != null) {
                    if (networkToUse != cable.ENERGY_STORAGE) {
                        // Found another network, merge its energy
                        if (oldNetworks.add(cable.ENERGY_STORAGE)) {
                            totalEnergy += cable.ENERGY_STORAGE.getEnergyStored();
                            cable.ENERGY_STORAGE.removeAllBlockEntities(connectedCables);
                            networkToUse.addBlockEntity(connectedCables);
                        }
                    }
                } else {
                    // Cable is not part of any network, add it to the new one
                    networkToUse.addBlockEntity(cable);
                }
            }

            // Remove old networks that are not part of the new network
            if (!connectedCables.isEmpty()) {
                for (CableBlockEntity cableBlockEntity : networkToUse.getConnectedBlockEntities().stream().toList()) {
                    if (!connectedCables.contains(cableBlockEntity)) {
                        // Remove cables that are not part of the new network
                        networkToUse.removeBlockEntity(cableBlockEntity);
                        cableBlockEntity.ENERGY_STORAGE = null;
                        LOG.warn("Removed disconnected cable, This should not happen: {}", cableBlockEntity);
                    }
                }
            }
        }

        // Create new network if none found
        if (networkToUse == null) {
            networkToUse = new ModSharedEnergyStorage<>(connectedCables,
                    ENERGY_STORAGE_AMOUNT_PER_CABLE,
                    ENERGY_TRANSFER_AMOUNT);
        }

        // Update all cables to use this network
        for (CableBlockEntity cable : connectedCables) {
            cable.ENERGY_STORAGE = networkToUse;
        }

        // Set combined energy
        networkToUse.setEnergy(totalEnergy);

        connectedCables.stream().map(BlockEntity::getBlockPos).toList().forEach(stillToUpdate::remove);
    }

    private void collectConnectedCables(CableBlockEntity start, HashSet<CableBlockEntity> visited) {
        if (visited.contains(start) || level == null) {
            return;
        }

        visited.add(start);

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = start.worldPosition.relative(direction);
            BlockEntity neighborEntity = level.getBlockEntity(neighborPos);

            if (neighborEntity instanceof CableBlockEntity cable && !cable.isRemoved()) {
                collectConnectedCables(cable, visited);
            }
        }
    }

    public void onRemove() {
        if (level == null || level.isClientSide || ENERGY_STORAGE == null) {
            return;
        }

        // Remove this cable from its network
        int energy = ENERGY_STORAGE.getEnergyStored();
        ENERGY_STORAGE.removeBlockEntity(this);
        ENERGY_STORAGE = null;
        setRemoved();

        // Collect neighbor cables and update their energy storage
        HashSet<CableBlockEntity> neighbors = new HashSet<>();
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = worldPosition.relative(direction);
            BlockEntity neighborEntity = level.getBlockEntity(neighborPos);

            if (neighborEntity instanceof CableBlockEntity cable && !cable.isRemoved()) {
                neighbors.add(cable);
            }
        }

        // If there's only 0-1 neighbors, no need to check for splits
        if (neighbors.size() <= 1) {
            return;
        }

        int totalCables = 0;
        HashSet<ModSharedEnergyStorage<CableBlockEntity>> newNetworks = new HashSet<>();
        for (CableBlockEntity neighbor : neighbors) {
            // Check if the neighbor is already part of a newly created network
            if (newNetworks.stream().anyMatch(network -> network.getConnectedBlockEntities().contains(neighbor))) {
                continue;
            }

            // Create a new network for the neighbor
            HashSet<CableBlockEntity> networkCables = new HashSet<>();
            collectConnectedCables(neighbor, networkCables);
            totalCables += networkCables.size();
            ModSharedEnergyStorage<CableBlockEntity> network = new ModSharedEnergyStorage<>(
                    networkCables,
                    ENERGY_STORAGE_AMOUNT_PER_CABLE,
                    ENERGY_TRANSFER_AMOUNT);
            newNetworks.add(network);
        }

        // If we have more than 1 network, we need to split them
        if (newNetworks.size() > 1) {
            for (ModSharedEnergyStorage<CableBlockEntity> network : newNetworks) {
                // Set the energy of the new network to the total energy of the old network
                network.setEnergy(energy * network.getConnectedBlockEntities().size() / totalCables);
            }
        } else {
            newNetworks.stream().findFirst().ifPresent(network -> network.setEnergy(energy));
        }

        for (ModSharedEnergyStorage<CableBlockEntity> network : newNetworks) {
            for (CableBlockEntity cable : network.getConnectedBlockEntities()) {
                cable.ENERGY_STORAGE = network;
            }
            // Notify the network that its energy has changed to update the block entities
            network.onEnergyChanged();
        }

        // Ensure that the energy storage is null after removal and was not re-added to a network
        if (ENERGY_STORAGE != null) {
            throw new IllegalStateException("Energy storage is not null after removal");
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!stillToUpdate.isEmpty()) {
            LOG.warn("Updating {} un-updated cables", stillToUpdate.size());
            for (BlockPos posToUpdate : new ArrayList<>(stillToUpdate)) {
                BlockEntity blockEntity = level.getBlockEntity(posToUpdate);
                if (blockEntity instanceof CableBlockEntity cableBlockEntity) {
                    LOG.info("Updating un-updated cable at {}", posToUpdate);
                    cableBlockEntity.updateEnergyStorage();
                } else {
                    LOG.warn("Un-updated cable at {} is not a cable block entity, skipping it", posToUpdate);
                    stillToUpdate.remove(posToUpdate);
                }
            }
        }

        pushEnergyToNeighbors();
    }

    private void pushEnergyToNeighbors() {
        assert this.level != null;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = this.worldPosition.relative(direction);
            if (ModEnergyUtil.doesBlockHaveEnergyStorage(neighborPos, this.level)) {
                ModEnergyUtil.move(this.worldPosition, neighborPos, ENERGY_TRANSFER_AMOUNT, this.level);
            }
        }
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return this.ENERGY_STORAGE;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (ENERGY_STORAGE == null) {
            LOG.warn("Updating null energy storage for cable when saving at {}", this.worldPosition);
            updateEnergyStorage();
        }
        if (ENERGY_STORAGE != null) {
            tag.put("cable.energy", ENERGY_STORAGE.serializeNBT(this, registries));
        } else {
            LOG.warn("Energy storage is null for cable when saving at {} on {}", this.worldPosition, this.level == null ? "unknown" : this.level.isClientSide ? "client" : "server");
        }

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (level == null || level.isClientSide) {
            return;
        }
//        if (ENERGY_STORAGE == null) {
//            LOG.warn("Loading null energy storage for cable at {} on {}", this.worldPosition, this.level == null ? "unknown" : this.level.isClientSide ? "client" : "server");
//            updateEnergyStorage();
//        }
        if (ENERGY_STORAGE != null) {
            ENERGY_STORAGE.deserializeNBT(this, registries, tag.getCompound("cable.energy"));
        } else {
            LOG.warn("Creating new energy storage from NBT for cable at {}", this.worldPosition);
            ENERGY_STORAGE = ModSharedEnergyStorage.deserializeNBT(this, ENERGY_STORAGE_AMOUNT_PER_CABLE, ENERGY_TRANSFER_AMOUNT,
                    registries, tag.getCompound("cable.energy"));
            LOG.info("Created new energy storage with {} cables", ENERGY_STORAGE.getConnectedBlockEntities().size());
            for (CableBlockEntity connectedBlockEntity : ENERGY_STORAGE.getConnectedBlockEntities()) {
                if (connectedBlockEntity != this && connectedBlockEntity.ENERGY_STORAGE != ENERGY_STORAGE) {
                    connectedBlockEntity.ENERGY_STORAGE = ENERGY_STORAGE;
                }
            }
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }

    @Override
    public String toString() {
        return String.format("%s{pos=%s, energyStorage=%s}", getClass().getSimpleName(), worldPosition, ENERGY_STORAGE);
    }
}
