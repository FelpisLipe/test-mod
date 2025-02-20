package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TestMod.MOD_ID, exFileHelper);
    }
        // Block shit
    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.TROLL_BLOCK);
        blockWithItem(ModBlocks.SMILEY_ORE);
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


        blockItem(ModBlocks.TROLL_STAIRS);
        blockItem(ModBlocks.TROLL_SLAB);
        blockItem(ModBlocks.TROLL_PRESSURE_PLATE);
        blockItem(ModBlocks.TROLL_FENCE_GATE);
        blockItem(ModBlocks.TROLL_TRAPDOOR, "_bottom");
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
