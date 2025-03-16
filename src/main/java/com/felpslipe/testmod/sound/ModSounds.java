package com.felpslipe.testmod.sound;

import com.felpslipe.testmod.TestMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, TestMod.MOD_ID);

    public static final Supplier<SoundEvent> THIRTY_USE = registerSoundEvent("thirty_use");
    public static final Supplier<SoundEvent> SHADY_BLOCK_BREAK = registerSoundEvent("shady_block_break");
    public static final Supplier<SoundEvent> SHADY_BLOCK_PLACE = registerSoundEvent("shady_block_place");
    public static final Supplier<SoundEvent> SHADY_BLOCK_STEP = registerSoundEvent("shady_block_step");
    public static final Supplier<SoundEvent> SHADY_BLOCK_HIT = registerSoundEvent("shady_block_hit");
    public static final Supplier<SoundEvent> SHADY_BLOCK_FALL = registerSoundEvent("shady_block_fall");
    public static final Supplier<SoundEvent> CABELA_AMBIENT = registerSoundEvent("cabela_ambient");
    public static final Supplier<SoundEvent> CABELA_CRY_AMBIENT = registerSoundEvent("cabela_cry_ambient");
    public static final Supplier<SoundEvent> CABELA_HURT = registerSoundEvent("cabela_hurt");
    public static final Supplier<SoundEvent> CABELA_DEATH = registerSoundEvent("cabela_death");

    public static final DeferredSoundType SHADY_BLOCK_SOUNDS= new DeferredSoundType(1f, 1f, ModSounds.SHADY_BLOCK_BREAK, ModSounds.SHADY_BLOCK_PLACE,
            ModSounds.SHADY_BLOCK_STEP, ModSounds.SHADY_BLOCK_HIT, ModSounds.SHADY_BLOCK_FALL);


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
