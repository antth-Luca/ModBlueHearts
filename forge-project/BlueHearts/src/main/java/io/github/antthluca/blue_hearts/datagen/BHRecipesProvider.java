package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitFoods;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class BHRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public BHRecipesProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Shaped
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, InitItems.MYST_BLUE_GEM.get())
                .pattern(" GR")
                .pattern("GEG")
                .pattern("RG ")
                .define('G', Items.GOLD_INGOT)
                .define('R', Items.ROTTEN_FLESH)
                .define('E', InitItems.BLUE_EYE.get())
                .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .unlockedBy("has_blue_eye", has(InitItems.BLUE_EYE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, InitItems.HEART_MARBLEMAROON.get())
                .pattern("R R")
                .pattern("BDO")
                .pattern(" Q ")
                .define('R', Items.RED_DYE)
                .define('B', InitItems.BLUE_BLOOD_POTION.get())
                .define('D', Items.DIAMOND_BLOCK)
                .define('O', InitItems.ORANGE_ANTIDOTE_POTION.get())
                .define('Q', Items.QUARTZ)
                .unlockedBy("has_red_dye", has(Items.RED_DYE))
                .unlockedBy("has_blue_blood", has(InitItems.BLUE_BLOOD_POTION.get()))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_orange_potion", has(InitItems.ORANGE_ANTIDOTE_POTION.get()))
                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, InitFoods.LAZULI_APPLE.get())
                .pattern("LLL")
                .pattern("LAL")
                .pattern("LLL")
                .define('L', Items.LAPIS_LAZULI)
                .define('A', Items.GOLDEN_APPLE)
                .unlockedBy("has_lapis", has(Items.LAPIS_LAZULI))
                .unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE))
                .save(consumer);

        // Shapeless
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, InitItems.VITAL_SAP.get())
                .requires(InitFoods.VITAL_FRUIT.get())
                .requires(InitFoods.VITAL_FRUIT.get())
                .unlockedBy("has_vital_fruit", has(InitFoods.VITAL_FRUIT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, InitItems.BLUE_EYE.get())
                .requires(Items.BLAZE_POWDER)
                .requires(Items.ENDER_PEARL)
                .requires(InitItems.VITAL_SAP.get())
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL))
                .unlockedBy("has_vital_sap", has(InitItems.VITAL_SAP.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID, "blue_eye_apart"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, InitItems.BLUE_EYE.get())
                .requires(Items.ENDER_EYE)
                .requires(InitItems.VITAL_SAP.get())
                .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
                .unlockedBy("has_vital_sap", has(InitItems.VITAL_SAP.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID, "blue_eye_partial"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, InitItems.PETRIFIED_BLUE_BLOOD.get())
                .requires(InitItems.BLUE_BLOOD_POTION.get())
                .requires(Items.DIAMOND)
                .requires(Items.BONE_BLOCK)
                .unlockedBy("has_blue_blood", has(InitItems.BLUE_BLOOD_POTION.get()))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .unlockedBy("has_bone_block", has(Items.BONE_BLOCK))
                .save(consumer);
    }
}
