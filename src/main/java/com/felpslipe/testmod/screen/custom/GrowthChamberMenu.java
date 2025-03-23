package com.felpslipe.testmod.screen.custom;

import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.block.entity.GrowthChamberBlockEntity;
import com.felpslipe.testmod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class GrowthChamberMenu extends ModBlockEntityMenu<GrowthChamberBlockEntity> {
    private final ContainerData data;

    public GrowthChamberMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public GrowthChamberMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.GROWTH_CHAMBER_MENU.get(), containerId, inv, (GrowthChamberBlockEntity) entity, ModBlocks.GROWTH_CHAMBER);
        this.data = data;

        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 54, 34));
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 104, 34));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }
}
