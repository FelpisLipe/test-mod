package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.TROLL_BLOCK.get());
        dropSelf(ModBlocks.SHADY_BLOCK.get());
        add(ModBlocks.SMILEY_ORE.get(),
                block -> createOreDrop(ModBlocks.SMILEY_ORE.get(), ModItems.SMILEY.get()));
        dropSelf(ModBlocks.TROLL_STAIRS.get());
        add(ModBlocks.TROLL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TROLL_SLAB.get()));
        dropSelf(ModBlocks.TROLL_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.TROLL_BUTTON.get());
        dropSelf(ModBlocks.TROLL_FENCE.get());
        dropSelf(ModBlocks.TROLL_FENCE_GATE.get());
        dropSelf(ModBlocks.TROLL_WALL.get());
        add(ModBlocks.TROLL_DOOR.get(),
                block -> createDoorTable(ModBlocks.TROLL_DOOR.get()));
        dropSelf(ModBlocks.TROLL_TRAPDOOR.get());
        dropSelf(ModBlocks.THIRTY_LAMP.get());
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
