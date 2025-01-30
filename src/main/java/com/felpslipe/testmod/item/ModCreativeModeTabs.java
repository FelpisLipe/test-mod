package com.felpslipe.testmod.item;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TestMod.MOD_ID);

    public static final Supplier<CreativeModeTab> TROLL_ITEMS_TAB = CREATIVE_MODE_TAB.register("troll_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.TROLL.get()))
                    .title(Component.translatable("creativetab.testmod.troll_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.TROLL);
                        output.accept(ModItems.SMILEY);
                        output.accept(ModItems.THIRTY);
                    })
                    .build());


    public static final Supplier<CreativeModeTab> TROLL_BLOCKS_TAB = CREATIVE_MODE_TAB.register("troll_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.TROLL_BLOCK))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "troll_items_tab"))
                    .title(Component.translatable("creativetab.testmod.troll_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.TROLL_BLOCK);
                        output.accept(ModBlocks.SMILEY_ORE);
                        output.accept(ModBlocks.SHADY_BLOCK);
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
