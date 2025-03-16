package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.item.ModItems;
import com.felpslipe.testmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
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
        tag(ItemTags.SWORDS)
                .add(ModItems.SMILEY_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.SMILEY_PICKAXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.SMILEY_SHOVEL.get());
        tag(ItemTags.AXES)
                .add(ModItems.SMILEY_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.SMILEY_HOE.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.SMILEY_HELMET.get())
                .add(ModItems.SMILEY_CHESTPLATE.get())
                .add(ModItems.SMILEY_LEGGINGS.get())
                .add(ModItems.SMILEY_BOOTS.get());
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.VIRAL_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_VIRAL_LOG.get().asItem())
                .add(ModBlocks.VIRAL_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_VIRAL_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.VIRAL_PLANKS.get().asItem());
        tag(ItemTags.SKULLS)
                .add(ModItems.CABELA_HEAD.get().asItem())
                .add(ModItems.CABELA_CRY_HEAD.get().asItem());

    }
}
