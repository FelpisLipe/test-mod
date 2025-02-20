package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TestMod.MOD_ID, existingFileHelper);
    }
        // Just for items
    @Override
    protected void registerModels() {
        basicItem(ModItems.SMILEY.get());
        basicItem(ModItems.TROLL.get());
        basicItem(ModItems.FRANGO.get());
        basicItem(ModItems.HOT_BREATH.get());
        basicItem(ModItems.HOT_CHEETO.get());
        basicItem(ModItems.THIRTY.get());
        buttonItem(ModBlocks.TROLL_BUTTON, ModBlocks.TROLL_BLOCK);
        fenceItem(ModBlocks.TROLL_FENCE, ModBlocks.TROLL_BLOCK);
        wallItem(ModBlocks.TROLL_WALL, ModBlocks.TROLL_BLOCK);
        basicItem(ModBlocks.TROLL_DOOR.asItem());


    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + baseBlock.getId().getPath()));
    }
    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + baseBlock.getId().getPath()));
    }
    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "block/" + baseBlock.getId().getPath()));
    }
}
