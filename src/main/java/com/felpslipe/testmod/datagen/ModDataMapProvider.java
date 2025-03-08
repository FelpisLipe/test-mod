package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.HOT_BREATH.getId(), new FurnaceFuel(1600),false)
                .add(ModItems.HOT_CHEETO.getId(), new FurnaceFuel(2000),false);

        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.FRANGO_SEEDS.getId(), new Compostable(0.12f), false)
                .add(ModItems.FRANGO.getId(), new Compostable(0.69f), false);
    }
}
