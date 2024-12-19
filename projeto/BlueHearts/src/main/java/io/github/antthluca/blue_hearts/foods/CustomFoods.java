package io.github.antthluca.blue_hearts.foods;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class CustomFoods {
    public static final FoodProperties VITAL_FRUIT_FOOD = new FoodProperties.Builder()
        .nutrition(2)
        .saturationMod(0.2f)
        .build();

    public static final FoodProperties LAZULI_APPLE_FOOD = new FoodProperties.Builder()
        .nutrition(12)
        .saturationMod(19.2f)
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
}
