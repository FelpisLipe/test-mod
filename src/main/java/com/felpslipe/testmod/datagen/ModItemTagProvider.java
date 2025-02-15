package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.item.ModItems;
import com.felpslipe.testmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TestMod.MOD_ID, existingFileHelper);
    }

    // Item tags (duh)
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.RATTABLE_ITEMS)
                .add(ModItems.SMILEY.get())
                .add(Items.NETHERITE_INGOT)
                .add(Items.NETHERITE_BLOCK);
    }
}
