package io.github.antthluca.blue_hearts.init;

import net.minecraft.world.item.Items;
import io.github.antthluca.blue_hearts.utils.BetterBrewingRecipe;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class InitRecipes {
    public static void registerBrewingRecipes() {
        // Blue Blood Potion
        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(
            Potions.REGENERATION, // Poção base
            InitItems.VITAL_SAP.get(), // Ingrediente
            InitPotions.BLUE_BLOOD_POTION.get() // Resultado
        ));

        // Orange Antidote Potion
        BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(
            Potions.POISON, // Poção base
            Items.ROTTEN_FLESH, // Ingrediente
            InitPotions.ORANGE_ANTIDOTE_POTION.get() // Resultado
        ));
    }
}
