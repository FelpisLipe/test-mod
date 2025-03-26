package com.felpslipe.testmod.fluid;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, TestMod.MOD_ID);

    public static final Supplier<FlowingFluid> SOURCE_BLACK_OPAL_WATER = FLUIDS.register("source_black_opal_water",
            () -> new BaseFlowingFluid.Source(ModFluids.BLACK_OPAL_WATER_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_BLACK_OPAL_WATER = FLUIDS.register("flowing_black_opal_water",
            () -> new BaseFlowingFluid.Flowing(ModFluids.BLACK_OPAL_WATER_PROPERTIES));

    public static final DeferredBlock<LiquidBlock> BLACK_OPAL_WATER_BLOCK = ModBlocks.BLOCKS.register("black_opal_water_block",
            () -> new LiquidBlock(ModFluids.SOURCE_BLACK_OPAL_WATER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    public static final DeferredItem<Item> BLACK_OPAL_WATER_BUCKET = ModItems.ITEMS.registerItem("black_opal_water_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_BLACK_OPAL_WATER.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final BaseFlowingFluid.Properties BLACK_OPAL_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.BLACK_OPAL_WATER_FLUID_TYPE, SOURCE_BLACK_OPAL_WATER, FLOWING_BLACK_OPAL_WATER)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModFluids.BLACK_OPAL_WATER_BLOCK).bucket(ModFluids.BLACK_OPAL_WATER_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
