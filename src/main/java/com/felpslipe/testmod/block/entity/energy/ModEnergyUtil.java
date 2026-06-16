package com.felpslipe.testmod.block.entity.energy;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModEnergyUtil {
    /**
     * Move energy from one block to another
     * @param from The position of the block to extract energy from
     * @param to The position of the block to receive energy
     * @param amount The amount of energy to move
     * @param level The level of the world
     * @return true if the energy was moved successfully, false otherwise
     */
    public static boolean move(BlockPos from, BlockPos to, int amount, Level level) {
        IEnergyStorage fromStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, from, null);
        IEnergyStorage toStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, to, null);

        if (fromStorage == null || toStorage == null
                || fromStorage.equals(toStorage)
                || energyStorageCannotExtractEnergy(fromStorage)
                || energyStorageCannotReceiveEnergy(toStorage)) {
            return false;
        }

        int maxAmountToReceive = toStorage.receiveEnergy(amount, true);

        int extractedEnergy = fromStorage.extractEnergy(maxAmountToReceive, false);
        toStorage.receiveEnergy(extractedEnergy, false);

        return true;
    }

    private static boolean energyStorageCannotReceiveEnergy(IEnergyStorage toStorage) {
        // No more Energy to draw or cannot extract
        return toStorage.getEnergyStored() >= toStorage.getMaxEnergyStored() || !toStorage.canReceive();
    }

    private static boolean energyStorageCannotExtractEnergy(IEnergyStorage fromStorage) {
        // No more Space to receive or cannot receive
        return fromStorage.getEnergyStored() < 1 || !fromStorage.canExtract();
    }

    /**
     * Check if the block at the given position has an energy storage
     * @param positionToCheck The position of the block to check
     * @param level The level of the world
     * @return true if the block has an energy storage, false otherwise
     */
    public static boolean doesBlockHaveEnergyStorage(BlockPos positionToCheck, Level level) {
        return level.getBlockEntity(positionToCheck) != null && level.getCapability(Capabilities.EnergyStorage.BLOCK, positionToCheck, null) != null;
    }
}
