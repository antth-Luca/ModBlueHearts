package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFoods {
    public static final DeferredRegister<Item> FOODS = DeferredRegister.create(
        ForgeRegistries.ITEMS, BlueHearts.MODID);

    // Food Properties
    private static final FoodProperties VITAL_FRUIT_PROP = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.2F)
            .build();

    private static final FoodProperties LAZULI_APPLE_PROP = new FoodProperties.Builder()
            .nutrition(12)
            .saturationMod(19.2f)
            .alwaysEat()
            .effect(() -> new MobEffectInstance(
                MobEffects.ABSORPTION, 1800, 1 // Absorção II por 1:30
            ), 1.0F)
            .effect(() -> new MobEffectInstance(
                MobEffects.REGENERATION, 200, 1 // Regeneração II por 0:10
            ), 1.0F)
            .effect(() -> new MobEffectInstance(
                MobEffects.HEAL, 1, 2 // Cura III
            ), 1.0F)
            .effect(() -> new MobEffectInstance(
                MobEffects.DAMAGE_BOOST, 1200, 0 // Força por 1:00
            ), 1.0F)
            .effect(() -> new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED, 1200, 0 // Velocidade por 1:00
            ), 1.0F)
            .build();

    // Foods
    public static final RegistryObject<BlockItem> VITAL_FRUIT = FOODS.register(
        "vital_fruit", () -> new ItemNameBlockItem(
            InitBlocks.VITAL_BUSH.get(),
            new Item.Properties()
                    .food(VITAL_FRUIT_PROP)));

    public static final RegistryObject<Item> LAZULI_APPLE = FOODS.register(
        "lazuli_apple", () -> new Item(new Item.Properties()
                .food(LAZULI_APPLE_PROP)
                .rarity(Rarity.RARE)));
}