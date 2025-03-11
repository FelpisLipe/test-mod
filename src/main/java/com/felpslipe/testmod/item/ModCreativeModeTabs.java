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
                        output.accept(ModItems.FRANGO);
                        output.accept(ModItems.HOT_BREATH);
                        output.accept(ModItems.HOT_CHEETO);
                        output.accept(ModItems.SMILEY_SWORD);
                        output.accept(ModItems.SMILEY_SHOVEL);
                        output.accept(ModItems.SMILEY_PICKAXE);
                        output.accept(ModItems.SMILEY_AXE);
                        output.accept(ModItems.SMILEY_HOE);
                        output.accept(ModItems.SMILEY_HAMMER);
                        output.accept(ModItems.SMILEY_HELMET);
                        output.accept(ModItems.SMILEY_CHESTPLATE);
                        output.accept(ModItems.SMILEY_LEGGINGS);
                        output.accept(ModItems.SMILEY_BOOTS);
                        output.accept(ModItems.FRANGO_SEEDS);
                        output.accept(ModItems.CABELA_SPAWN_EGG);
                    })
                    .build());


    public static final Supplier<CreativeModeTab> TROLL_BLOCKS_TAB = CREATIVE_MODE_TAB.register("troll_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.TROLL_BLOCK))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "troll_items_tab"))
                    .title(Component.translatable("creativetab.testmod.troll_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.TROLL_BLOCK);
                        output.accept(ModBlocks.TROLL_STAIRS);
                        output.accept(ModBlocks.TROLL_SLAB);
                        output.accept(ModBlocks.TROLL_PRESSURE_PLATE);
                        output.accept(ModBlocks.TROLL_BUTTON);
                        output.accept(ModBlocks.TROLL_FENCE);
                        output.accept(ModBlocks.TROLL_FENCE_GATE);
                        output.accept(ModBlocks.TROLL_WALL);
                        output.accept(ModBlocks.TROLL_DOOR);
                        output.accept(ModBlocks.TROLL_TRAPDOOR);
                        output.accept(ModBlocks.SMILEY_ORE);
                        output.accept(ModBlocks.DEEPSLATE_SMILEY_ORE);
                        output.accept(ModBlocks.NETHER_SMILEY_ORE);
                        output.accept(ModBlocks.END_SMILEY_ORE);
                        output.accept(ModBlocks.SHADY_BLOCK);
                        output.accept(ModBlocks.THIRTY_LAMP);
                        output.accept(ModBlocks.VIRAL_LOG);
                        output.accept(ModBlocks.STRIPPED_VIRAL_LOG);
                        output.accept(ModBlocks.VIRAL_WOOD);
                        output.accept(ModBlocks.STRIPPED_VIRAL_WOOD);
                        output.accept(ModBlocks.VIRAL_PLANKS);
                        output.accept(ModBlocks.VIRAL_SAPLING);
                        output.accept(ModBlocks.VIRAL_LEAVES);
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
