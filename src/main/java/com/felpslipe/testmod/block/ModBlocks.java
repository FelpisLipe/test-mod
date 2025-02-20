package com.felpslipe.testmod.block;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.custom.ShadyBlock;
import com.felpslipe.testmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TestMod.MOD_ID);

    // Registering new block (troll block)
    public static final DeferredBlock<Block> TROLL_BLOCK = registerBlock("troll_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> SMILEY_ORE = registerBlock("smiley_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 9),
                    BlockBehaviour.Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SHADY_BLOCK = registerBlock("shady_block",
            () -> new ShadyBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<StairBlock> TROLL_STAIRS = registerBlock("troll_stairs",
            () -> new StairBlock(ModBlocks.TROLL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> TROLL_SLAB = registerBlock("troll_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<PressurePlateBlock> TROLL_PRESSURE_PLATE = registerBlock("troll_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<ButtonBlock> TROLL_BUTTON = registerBlock("troll_button",
            () -> new ButtonBlock(BlockSetType.STONE, 200, BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()
                            .noCollission()));

    public static final DeferredBlock<FenceBlock> TROLL_FENCE = registerBlock("troll_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<FenceGateBlock> TROLL_FENCE_GATE = registerBlock("troll_fence_gate",
            () -> new FenceGateBlock(WoodType.DARK_OAK, BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<WallBlock> TROLL_WALL = registerBlock("troll_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<DoorBlock> TROLL_DOOR = registerBlock("troll_door",
            () -> new DoorBlock(BlockSetType.DARK_OAK,BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()));

    public static final DeferredBlock<TrapDoorBlock> TROLL_TRAPDOOR = registerBlock("troll_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.DARK_OAK,BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    // Registering blocks
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
