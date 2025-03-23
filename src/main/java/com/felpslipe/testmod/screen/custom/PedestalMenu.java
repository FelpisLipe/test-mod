package com.felpslipe.testmod.screen.custom;

import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.block.entity.PedestalBlockEntity;
import com.felpslipe.testmod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class PedestalMenu extends ModBlockEntityMenu<PedestalBlockEntity> {

    public PedestalMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public PedestalMenu(int containerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.PEDESTAL_MENU.get(), containerId, inv, (PedestalBlockEntity) blockEntity, ModBlocks.PEDESTAL);

        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 0, 80, 35));
    }
}
