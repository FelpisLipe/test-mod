package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TestMod.MOD_ID, existingFileHelper);
    }
        // Just for items
    @Override
    protected void registerModels() {
        basicItem(ModItems.SMILEY.get());
        basicItem(ModItems.TROLL.get());
        basicItem(ModItems.FRANGO.get());
        basicItem(ModItems.HOT_BREATH.get());
        basicItem(ModItems.HOT_CHEETO.get());
        basicItem(ModItems.THIRTY.get());
    }
}
