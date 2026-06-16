package com.felpslipe.testmod.entity;

import com.felpslipe.testmod.TestMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;

public enum CabelaVariant implements SkullBlock.Type {
    NORMAL("cabela_normal"),
    CRY("cabela_cry");

    private static final CabelaVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CabelaVariant::getId)).toArray(CabelaVariant[]::new);
    private final String name;
    private final ResourceLocation resourceLocation;

    CabelaVariant(String name) {
        this.name = name;
        this.resourceLocation = ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/entity/cabela/" + name + ".png");
        TYPES.put(name, this);
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public @NotNull String getSerializedName() {
        return TestMod.MOD_ID + ":" + this.name;
    }

    public int getId() {
        return ordinal();
    }

    public static CabelaVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
