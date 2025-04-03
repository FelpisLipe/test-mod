package com.felpslipe.testmod.screen.custom;

import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.block.entity.CoalGeneratorBlockEntity;
import com.felpslipe.testmod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.neoforged.neoforge.items.SlotItemHandler;

public class CoalGeneratorMenu extends ModBlockEntityMenu<CoalGeneratorBlockEntity> {
    private final ContainerData data;

    public CoalGeneratorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (CoalGeneratorBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public CoalGeneratorMenu(int containerId, Inventory inv, CoalGeneratorBlockEntity entity, ContainerData data) {
        super(ModMenuTypes.COAL_GENERATOR_MENU.get(), containerId, inv, entity, ModBlocks.COAL_GENERATOR);
        this.data = data;

        this.addSlot(new SlotItemHandler(this.blockEntity.itemHandler, 0, 80, 35));

        addDataSlots(data);
    }

    public int getBurnProgress() {
        return data.get(0);
    }

    public int getMaxBurnProgress() {
        return data.get(1);
    }

    public boolean isBurning() {
        return getBurnProgress() > 0;
    }

    public float getFuelProgress() {
        int i = getMaxBurnProgress();
        if (i == 0) {
            return 0;
        }

        return Mth.clamp((float) getBurnProgress() / (float) i, 0.0f, 1.0f);
    }
}
