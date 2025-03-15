package com.felpslipe.testmod.entity;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.entity.custom.CabelaEntity;
import com.felpslipe.testmod.entity.custom.ToiletEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, TestMod.MOD_ID);

    public static final Supplier<EntityType<CabelaEntity>> CABELA = ENTITY_TYPES.register("cabela", () -> EntityType.Builder.of(CabelaEntity::new, MobCategory.CREATURE).sized(0.6f, 1.25f).build("cabela"));

    public static final Supplier<EntityType<ToiletEntity>> TOILET_ENTITY = ENTITY_TYPES.register("toilet_entity", () -> EntityType.Builder.of(ToiletEntity::new, MobCategory.MISC).sized(0.5f,0.5625f).build("toilet_entity"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
