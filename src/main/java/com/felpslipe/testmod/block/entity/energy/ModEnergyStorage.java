package com.felpslipe.testmod.block.entity.energy;

import net.minecraft.util.Mth;
import net.neoforged.neoforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {
    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ModEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(toExtract, simulate);
        if (extractedEnergy != 0 && !simulate) {
            onEnergyChanged();
        }

        return extractedEnergy;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        int receiveEnergy = super.receiveEnergy(toReceive, simulate);
        if (receiveEnergy != 0 && !simulate) {
            onEnergyChanged();
        }

        return receiveEnergy;
    }

    public int receiveEnergyInternal(int toReceive, boolean simulate) {
        if (toReceive > 0) {
            int energyReceived = Mth.clamp(this.capacity - this.energy, 0, toReceive);
            if (!simulate) {
                this.energy += energyReceived;
                if (energyReceived != 0) {
                    onEnergyChanged();
                }
            }

            return energyReceived;
        } else {
            return 0;
        }
    }

    public int extractEnergyInternal(int toExtract, boolean simulate) {
        if (toExtract > 0) {
            int energyExtracted = Math.min(this.energy, toExtract);
            if (!simulate) {
                this.energy -= energyExtracted;
                if (energyExtracted != 0) {
                    onEnergyChanged();
                }
            }

            return energyExtracted;
        } else {
            return 0;
        }
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();
}
