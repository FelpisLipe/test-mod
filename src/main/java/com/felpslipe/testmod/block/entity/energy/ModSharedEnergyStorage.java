package com.felpslipe.testmod.block.entity.energy;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ModSharedEnergyStorage extends ModEnergyStorage {
    private final ArrayList<BlockEntity> blockEntities;
    public ModSharedEnergyStorage(BlockEntity blockEntity, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        ArrayList<BlockEntity> blockEntities = new ArrayList<>();
        blockEntities.add(blockEntity);
        this.blockEntities = blockEntities;
    }
    public ModSharedEnergyStorage(List<BlockEntity> blockEntity, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.blockEntities = new ArrayList<>(blockEntity);
    }

    public void addBlockEntity(BlockEntity blockEntity) {
        if (!blockEntities.contains(blockEntity)) {
            blockEntities.add(blockEntity);
        }
    }

    public void removeBlockEntity(BlockEntity blockEntity) {
        blockEntities.remove(blockEntity);
    }

    public Tag serializeNBT(BlockEntity blockEntity, HolderLookup.Provider provider) {
        if (blockEntities.contains(blockEntity)) {
            // TODO Make only serialise the single block's energy
            return super.serializeNBT(provider);
        }
        return null;
    }

    public void deserializeNBT(BlockEntity blockEntity, HolderLookup.Provider provider, Tag nbt) {
        if (blockEntities.contains(blockEntity)) {
            super.deserializeNBT(provider, nbt);
        }
    }

    @Override
    public void onEnergyChanged() {
        for (BlockEntity blockEntity : blockEntities) {
            if (blockEntity != null) {
                blockEntity.setChanged();
                Level level = blockEntity.getLevel();
                if (level != null) {
                    BlockState blockState = blockEntity.getBlockState();
                    level.sendBlockUpdated(blockEntity.getBlockPos(), blockState, blockState, 3);
                }
            }
        }
    }
}
