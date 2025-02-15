package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TestMod.MOD_ID, existingFileHelper);
    }
    // Block tags (duh)
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SMILEY_ORE.get())
                .add(ModBlocks.TROLL_BLOCK.get())
                .add(ModBlocks.SHADY_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SMILEY_ORE.get());
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.TROLL_BLOCK.get())
                .add(ModBlocks.SHADY_BLOCK.get());
    }

}
