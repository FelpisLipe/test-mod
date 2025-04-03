package com.felpslipe.testmod.block.entity.energy;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModBlockEntityEnergyStorage extends ModEnergyStorage {
    private final BlockEntity blockEntity;
    public ModBlockEntityEnergyStorage(BlockEntity blockEntity, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.blockEntity = blockEntity;
    }

    public ModBlockEntityEnergyStorage(BlockEntity blockEntity, int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.blockEntity = blockEntity;
    }

    @Override
    public void onEnergyChanged() {
        blockEntity.setChanged();
        BlockState blockState = blockEntity.getBlockState();
        blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), blockState, blockState, 3);
    }
}
