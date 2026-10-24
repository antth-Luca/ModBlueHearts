package io.github.antthluca.blue_hearts;

import io.github.antthluca.blue_hearts.config.BHCommonConfig;
import io.github.antthluca.blue_hearts.init.*;
import io.github.antthluca.blue_hearts.networking.BHMessages;
import io.github.antthluca.blue_hearts.recipes.BetterBrewingRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlueHearts.MODID)
public class BlueHearts {
    public static final String MODID = "blue_hearts";

    public BlueHearts() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Init
        InitMobEffects.MOB_EFFECTS.register(bus);
        InitFoods.FOODS.register(bus);
        InitItems.ITEMS.register(bus);
        InitBlocks.BLOCKS.register(bus);
        InitCreativeTabs.TABS.register(bus);

        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BHCommonConfig.SPEC, "bluehearts-common.toml");

        bus.addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Networking
            BHMessages.register();

            // Brewing Recipes
            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(
                Potions.REGENERATION,
                InitItems.VITAL_SAP.get(),
                InitItems.BLUE_BLOOD_POTION.get()
            ));
            BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(
                Potions.POISON,
                Items.ROTTEN_FLESH,
                InitItems.ORANGE_ANTIDOTE_POTION.get()
            ));
        });
    }
}
