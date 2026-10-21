package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitItems;
import io.github.antthluca.blue_hearts.recipes.BetterBrewingRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = BlueHearts.MODID)
public class BHRegisterBrewingRecipes {
    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        // Blue Blood Potion
        builder.addRecipe(new BetterBrewingRecipe(
                Potions.REGENERATION,
                InitItems.VITAL_SAP.get(),
                InitItems.BLUE_BLOOD_POTION.get()
        ));

        // Orange Antidote Potion
        builder.addRecipe(new BetterBrewingRecipe(
                Potions.POISON,
                Items.ROTTEN_FLESH,
                InitItems.ORANGE_ANTIDOTE_POTION.get()
        ));
    }
}
