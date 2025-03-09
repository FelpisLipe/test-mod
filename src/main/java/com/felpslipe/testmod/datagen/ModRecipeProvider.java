package com.felpslipe.testmod.datagen;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> SMILEY_SMELTABLES = List.of(ModBlocks.SMILEY_ORE,
                ModBlocks.DEEPSLATE_SMILEY_ORE,
                ModBlocks.NETHER_SMILEY_ORE,
                ModBlocks.END_SMILEY_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TROLL_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.TROLL.get())
                .unlockedBy("has_troll", has(ModItems.TROLL)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TROLL.get(), 9)
                .requires(ModBlocks.TROLL_BLOCK)
                .unlockedBy("has_troll_block", has(ModBlocks.TROLL_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TROLL.get(), 64)
                .requires(Blocks.NETHERITE_BLOCK)
                .unlockedBy("has_netherite_block", has(Blocks.NETHERITE_BLOCK))
                .save(recipeOutput,"testmod:troll_from_netherite_block");

        oreSmelting(recipeOutput, SMILEY_SMELTABLES, RecipeCategory.MISC, ModItems.SMILEY.get(),0.25f,200,"smiley");
        oreBlasting(recipeOutput, SMILEY_SMELTABLES, RecipeCategory.MISC, ModItems.SMILEY.get(),0.25f,100,"smiley");

        stairBuilder(ModBlocks.TROLL_STAIRS.get(), Ingredient.of(ModItems.TROLL))
                .group("troll")
                .unlockedBy("has_troll",has(ModItems.TROLL))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TROLL_SLAB.get(), ModItems.TROLL.get());
        buttonBuilder(ModBlocks.TROLL_BUTTON.get(), Ingredient.of(ModItems.TROLL))
                .group("troll")
                .unlockedBy("has_troll",has(ModItems.TROLL))
                .save(recipeOutput);
        pressurePlate(recipeOutput, ModBlocks.TROLL_PRESSURE_PLATE.get(), ModItems.TROLL.get());
        fenceBuilder(ModBlocks.TROLL_FENCE.get(), Ingredient.of(ModItems.TROLL))
                .group("troll")
                .unlockedBy("has_troll",has(ModItems.TROLL))
                .save(recipeOutput);
        fenceGateBuilder(ModBlocks.TROLL_FENCE_GATE.get(), Ingredient.of(ModItems.TROLL))
                .group("troll")
                .unlockedBy("has_troll",has(ModItems.TROLL))
                .save(recipeOutput);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TROLL_WALL.get() ,ModItems.TROLL.get());
        doorBuilder(ModBlocks.TROLL_DOOR.get(), Ingredient.of(ModItems.TROLL))
                .group("troll")
                .unlockedBy("has_troll",has(ModItems.TROLL))
                .save(recipeOutput);
        trapdoorBuilder(ModBlocks.TROLL_TRAPDOOR.get(), Ingredient.of(ModItems.TROLL))
                .group("troll")
                .unlockedBy("has_troll",has(ModItems.TROLL))
                .save(recipeOutput);
    }


    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, TestMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}

