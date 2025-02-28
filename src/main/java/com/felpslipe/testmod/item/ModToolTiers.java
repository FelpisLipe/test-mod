package com.felpslipe.testmod.item;

import com.felpslipe.testmod.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier SMILEY = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_SMILEY_TOOL,
            1400, 8f, 4f, 20, () -> Ingredient.of(ModItems.SMILEY));
}
