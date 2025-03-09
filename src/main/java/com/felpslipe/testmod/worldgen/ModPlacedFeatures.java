package com.felpslipe.testmod.worldgen;

import com.felpslipe.testmod.TestMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

        public static final ResourceKey<PlacedFeature> SMILEY_ORE_PLACED_KEY = registerKey("smiley_ore_placed");
        public static final ResourceKey<PlacedFeature> NETHER_SMILEY_ORE_PLACED_KEY = registerKey("nether_smiley_ore_placed");
        public static final ResourceKey<PlacedFeature> END_SMILEY_ORE_PLACED_KEY = registerKey("end_smiley_ore_placed");

        public static void bootstrap(BootstrapContext<PlacedFeature> context) {
            var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

            register(context, SMILEY_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_SMILEY_ORE_KEY),
                    ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
            register(context, NETHER_SMILEY_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_SMILEY_ORE_KEY),
                    ModOrePlacement.commonOrePlacement(9, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80))));
            register(context, END_SMILEY_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_SMILEY_ORE_KEY),
                    ModOrePlacement.commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80))));
        }

        private static ResourceKey<PlacedFeature> registerKey(String name) {
            return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, name));
        }

        private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                     List<PlacementModifier> modifiers) {
            context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
        }
}
