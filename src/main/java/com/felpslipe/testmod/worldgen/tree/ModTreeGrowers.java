package com.felpslipe.testmod.worldgen.tree;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower VIRAL = new TreeGrower(TestMod.MOD_ID + ":viral",
            Optional.empty(), Optional.of(ModConfiguredFeatures.VIRAL_KEY), Optional.empty());
}
