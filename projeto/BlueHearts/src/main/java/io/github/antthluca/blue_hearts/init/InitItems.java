package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS, BlueHearts.MODID
    );

    // Items
    public static final RegistryObject<Item> VITAL_SAP = ITEMS.register(
        "vital_sap", () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_BREWING)));

    public static final RegistryObject<Item> VITAL_FRUIT = ITEMS.register(
        "vital_fruit", () -> new Item(new Item.Properties()
            .food(Foods.VITAL_FRUIT_FOOD)
            .tab(CreativeModeTab.TAB_FOOD)));

    // Foods
    public static final RegistryObject<Item> LAZULI_APPLE = ITEMS.register(
        "lazuli_apple", () -> new Item(new Item.Properties()
            .food(Foods.LAZULI_APPLE_FOOD)
            .rarity(Rarity.RARE)
            .tab(CreativeModeTab.TAB_FOOD)));

    // Food classes
    public static class Foods {
        public static final FoodProperties LAZULI_APPLE_FOOD = new FoodProperties.Builder()
            .nutrition(12)
            .saturationMod(19.2f / 12f)
            .alwaysEat()
            .effect(() -> new MobEffectInstance(
                MobEffects.ABSORPTION, 1800, 1), 1.0f // Absorção II por 1:30
            )
            .effect(() -> new MobEffectInstance(
                MobEffects.REGENERATION, 200, 1), 1.0f // Regeneração II por 0:10
            )
            .effect(() -> new MobEffectInstance(
                MobEffects.HEAL, 1, 2), 1.0f // Cura III
            )
            .effect(() -> new MobEffectInstance(
                MobEffects.DAMAGE_BOOST, 1200, 0), 1.0f // Força por 1:00
            )
            .effect(() -> new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0f // Velocidade por 1:00
            )
            .build();

            public static final FoodProperties VITAL_FRUIT_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.4f)
            .build();
    }
}
