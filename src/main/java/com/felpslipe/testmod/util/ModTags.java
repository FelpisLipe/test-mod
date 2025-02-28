package com.felpslipe.testmod.util;

import com.felpslipe.testmod.TestMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_SMILEY_TOOL = createTag("needs_smiley_tool");
        public static final TagKey<Block> INCORRECT_FOR_SMILEY_TOOL = createTag("incorrect_for_smiley_tool");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> RATTABLE_ITEMS = createTag("rattable_items");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, name));
        }
    }
}
