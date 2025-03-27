package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.block.custom.FrangoCropBlock;
import com.felpslipe.testmod.block.custom.ThirtyLampBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TestMod.MOD_ID, exFileHelper);
    }
        // Block shit
    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.TROLL_BLOCK);
        blockWithItem(ModBlocks.SMILEY_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_SMILEY_ORE);
        blockWithItem(ModBlocks.NETHER_SMILEY_ORE);
        blockWithItem(ModBlocks.END_SMILEY_ORE);
        blockWithItem(ModBlocks.SHADY_BLOCK);

        stairsBlock(ModBlocks.TROLL_STAIRS.get(), blockTexture(ModBlocks.TROLL_BLOCK.get()));
        slabBlock(ModBlocks.TROLL_SLAB.get(), blockTexture(ModBlocks.TROLL_BLOCK.get()), blockTexture(ModBlocks.TROLL_BLOCK.get()));
        buttonBlock(ModBlocks.TROLL_BUTTON.get(), blockTexture(ModBlocks.TROLL_BLOCK.get()));
        pressurePlateBlock(ModBlocks.TROLL_PRESSURE_PLATE.get(), blockTexture(ModBlocks.TROLL_BLOCK.get()));
        fenceBlock(ModBlocks.TROLL_FENCE.get(), blockTexture(ModBlocks.TROLL_BLOCK.get()));
        fenceGateBlock(ModBlocks.TROLL_FENCE_GATE.get(), blockTexture(ModBlocks.TROLL_BLOCK.get()));
        wallBlock(ModBlocks.TROLL_WALL.get(), blockTexture(ModBlocks.TROLL_BLOCK.get()));
        doorBlockWithRenderType(ModBlocks.TROLL_DOOR.get(), modLoc("block/troll_door_bottom"), modLoc("block/troll_door_top"),"cutout");
        trapdoorBlockWithRenderType(ModBlocks.TROLL_TRAPDOOR.get(), modLoc("block/troll_trapdoor"), true,"cutout");
        lampBlock(ModBlocks.THIRTY_LAMP);

        blockItem(ModBlocks.TROLL_STAIRS);
        blockItem(ModBlocks.TROLL_SLAB);
        blockItem(ModBlocks.TROLL_PRESSURE_PLATE);
        blockItem(ModBlocks.TROLL_FENCE_GATE);
        blockItem(ModBlocks.TROLL_TRAPDOOR, "_bottom");

        makeCrop(((CropBlock) ModBlocks.FRANGO_CROP.get()), "frango_crop_stage", "frango_crop_stage");

        logBlock(((RotatedPillarBlock) ModBlocks.VIRAL_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_VIRAL_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.VIRAL_WOOD.get()), blockTexture(ModBlocks.VIRAL_LOG.get()), blockTexture(ModBlocks.VIRAL_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_VIRAL_WOOD.get()), blockTexture(ModBlocks.STRIPPED_VIRAL_LOG.get()), blockTexture(ModBlocks.STRIPPED_VIRAL_LOG.get()));

        blockItem(ModBlocks.VIRAL_LOG);
        blockItem(ModBlocks.VIRAL_WOOD);
        blockWithItem(ModBlocks.VIRAL_PLANKS);
        blockItem(ModBlocks.STRIPPED_VIRAL_LOG);
        blockItem(ModBlocks.STRIPPED_VIRAL_WOOD);
        saplingBlock(ModBlocks.VIRAL_SAPLING);
        leavesBlock(ModBlocks.VIRAL_LEAVES);
        horizontalBlock(ModBlocks.GROWTH_CHAMBER.get(), models().orientable("testmod:growth_chamber",
                mcLoc("block/blast_furnace_side"),
                modLoc("block/growth_chamber_front"),
                mcLoc("block/blast_furnace_top")));
        blockItem(ModBlocks.GROWTH_CHAMBER);
        blockWithItem(ModBlocks.COAL_GENERATOR);

        for (DeferredBlock<? extends AbstractSkullBlock> skull : ModBlocks.SKULLS) {
            skullBlock(skull);
        }
    }

    private void skullBlock(DeferredBlock<?> deferredBlock) {
        getVariantBuilder(deferredBlock.get())
                .partialState()
                .setModels(new ConfiguredModel(models().getExistingFile(
                        ResourceLocation.withDefaultNamespace("block/skull"))));
    }

    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }


    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((FrangoCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + textureName + state.getValue(((FrangoCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void lampBlock(DeferredBlock<?> deferredBlock) {
        getVariantBuilder(deferredBlock.get()).forAllStates(state -> {
            if(state.getValue(ThirtyLampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(deferredBlock.getId() + "_on",
                        ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_on")))};
            }
            else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(deferredBlock.getId() + "_off",
                        ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_off")))};
            }
        });
        simpleBlockItem(deferredBlock.get(), models().cubeAll(deferredBlock.getId() + "_off",
                ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_off")));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("testmod:block/" + deferredBlock.getId().getPath()));
    }
    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("testmod:block/" + deferredBlock.getId().getPath() + appendix));
    }
}
