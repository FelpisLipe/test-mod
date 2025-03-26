package com.felpslipe.testmod.screen.custom;

import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.block.entity.CoalGeneratorBlockEntity;
import com.felpslipe.testmod.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorMenu extends ModBlockEntityMenu<CoalGeneratorBlockEntity> {
    private final ContainerData data;

    public CoalGeneratorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public CoalGeneratorMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.COAL_GENERATOR_MENU.get(), containerId, inv, (CoalGeneratorBlockEntity) entity, ModBlocks.COAL_GENERATOR);
        this.data = data;

        this.addSlot(new SlotItemHandler(this.blockEntity.itemHandler, 0, 80, 35));

        addDataSlots(data);
    }

    public boolean isBurning() {
        return data.get(0) < 160;
    }

    public float getFuelProgress() {
        int i = this.data.get(1);
        if(i == 0) {
            i = 160;
        }

        return Mth.clamp((float)this.data.get(0) / (float)i, 0.0f, 1.0f);
    }
}
