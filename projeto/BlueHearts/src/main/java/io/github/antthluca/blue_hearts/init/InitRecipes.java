package io.github.antthluca.blue_hearts.init;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class InitRecipes {
    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(
            Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), // Poção base
            Ingredient.of(InitItems.VITAL_SAP.get()), // Ingrediente
            PotionUtils.setPotion(new ItemStack(Items.POTION), InitPotions.BLUE_BLOOD_POTION.get()) // Resultado
        );
    }
}