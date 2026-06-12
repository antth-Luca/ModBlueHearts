package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitFoods {
    public static final DeferredRegister.Items FOODS = DeferredRegister.createItems(BlueHearts.MODID);

    // Food Properties
    private static final FoodProperties VITAL_FRUIT_PROP = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.2F)
            .build();

    private static final FoodProperties LAZULI_APPLE_PROP = new FoodProperties.Builder()
            .nutrition(12)
            .saturationModifier(19.2f)
            .alwaysEdible()
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

    // Foods
    public static final DeferredItem<Item> VITAL_FRUIT = FOODS.register(
            "vital_fruit", () -> new ItemNameBlockItem(
                    InitBlocks.VITAL_BUSH.get(),
                    new Item.Properties()
                            .component(DataComponents.FOOD, VITAL_FRUIT_PROP)));

    public static final DeferredItem<Item> LAZULI_APPLE = FOODS.register(
            "lazuli_apple", () -> new Item(new Item.Properties()
                    .component(DataComponents.FOOD, LAZULI_APPLE_PROP)
                    .rarity(Rarity.RARE)));
}