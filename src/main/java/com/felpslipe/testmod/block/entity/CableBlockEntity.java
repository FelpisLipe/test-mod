package com.felpslipe.testmod.block.entity;

import com.felpslipe.testmod.block.entity.energy.ModEnergyStorage;
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

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CableBlockEntity extends BlockEntity {

    private static final int ENERGY_TRANSFER_AMOUNT = 480;
    private static final int ENERGY_STORAGE_AMOUNT_PER_CABLE = 6400;

    private ModEnergyStorage ENERGY_STORAGE;

    public CableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR_BE.get(), pos, blockState);
        updateEnergyStorage();
    }

    private void updateEnergyStorage() {
        // TODO Check if adjacent blocks are cables and have shared energy storage and merge the energy storage
        ENERGY_STORAGE = new ModSharedEnergyStorage(this, ENERGY_STORAGE_AMOUNT_PER_CABLE, ENERGY_TRANSFER_AMOUNT);
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return this.ENERGY_STORAGE;
    }

    public void onRemove(BlockPos pos) {

    }

    public void tick(Level level, BlockPos pos, BlockState state) {
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

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("cable.energy", ENERGY_STORAGE.serializeNBT(registries));

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ENERGY_STORAGE.deserializeNBT(registries, tag.getCompound("cable.energy"));
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
}
