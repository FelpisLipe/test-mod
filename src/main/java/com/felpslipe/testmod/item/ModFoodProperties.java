package com.felpslipe.testmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties FRANGO = new FoodProperties.Builder().nutrition(5).saturationModifier(2f)
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1200), 1)
            .build();
}
