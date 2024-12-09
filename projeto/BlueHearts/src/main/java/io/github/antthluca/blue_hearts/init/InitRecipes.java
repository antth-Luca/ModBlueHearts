package io.github.antthluca.blue_hearts.init;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraft.world.item.crafting.Ingredient;

public class InitRecipes {
    public static void registerBrewingRecipes() {
        // Blue Blood Potion
        BrewingRecipeRegistry.addRecipe(
            Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), // Poção base
            Ingredient.of(InitItems.VITAL_SAP.get()), // Ingrediente
            PotionUtils.setPotion(new ItemStack(Items.POTION), InitPotions.BLUE_BLOOD_POTION.get()) // Resultado
        );

        // Orange Antidote Potion
        BrewingRecipeRegistry.addRecipe(
            Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON)), // Poção base
            Ingredient.of(Items.ROTTEN_FLESH), // Ingrediente
            PotionUtils.setPotion(new ItemStack(Items.POTION), InitPotions.ORANGE_ANTIDOTE_POTION.get()) // Resultado
        );
    }
}
