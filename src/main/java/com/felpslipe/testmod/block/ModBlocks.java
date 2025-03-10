package com.felpslipe.testmod.block;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.custom.FrangoCropBlock;
import com.felpslipe.testmod.block.custom.ModFlammableRotatedPillarBlock;
import com.felpslipe.testmod.block.custom.ShadyBlock;
import com.felpslipe.testmod.block.custom.ThirtyLampBlock;
import com.felpslipe.testmod.item.ModItems;
import com.felpslipe.testmod.sound.ModSounds;
import com.felpslipe.testmod.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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

    public static final DeferredBlock<Block> DEEPSLATE_SMILEY_ORE = registerBlock("deepslate_smiley_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 9),
                    BlockBehaviour.Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> NETHER_SMILEY_ORE = registerBlock("nether_smiley_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 9),
                    BlockBehaviour.Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> END_SMILEY_ORE = registerBlock("end_smiley_ore",
            () -> new DropExperienceBlock(UniformInt.of(5, 9),
                    BlockBehaviour.Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()));


    public static final DeferredBlock<Block> SHADY_BLOCK = registerBlock("shady_block",
            () -> new ShadyBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(ModSounds.SHADY_BLOCK_SOUNDS)));

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
    public static final DeferredBlock<Block> THIRTY_LAMP = registerBlock("thirty_lamp",
            () -> new ThirtyLampBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(ThirtyLampBlock.CLICKED) ? 15 : 0 )
                    .sound(SoundType.GLASS)));
    public static final DeferredBlock<Block> FRANGO_CROP = BLOCKS.register("frango_crop",
            () -> new FrangoCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<Block> VIRAL_LOG = registerBlock("viral_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> VIRAL_WOOD = registerBlock("viral_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_VIRAL_LOG = registerBlock("stripped_viral_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> STRIPPED_VIRAL_WOOD = registerBlock("stripped_viral_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final DeferredBlock<Block> VIRAL_PLANKS = registerBlock("viral_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final DeferredBlock<Block> VIRAL_LEAVES = registerBlock("viral_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 6;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    public static final DeferredBlock<Block> VIRAL_SAPLING = registerBlock("viral_sapling",
            () -> new SaplingBlock(ModTreeGrowers.VIRAL, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

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
