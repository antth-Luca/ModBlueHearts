package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

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
            .build();

    // Consumables
    private static final Consumable LAZULI_APPLE_CONSUMABLE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
                    new MobEffectInstance(
                            MobEffects.ABSORPTION, 1800, 1 // Absorção II por 1:30
                    ),
                    new MobEffectInstance(
                            MobEffects.REGENERATION, 200, 1 // Regeneração II por 0:10
                    ),
                    new MobEffectInstance(
                            MobEffects.INSTANT_HEALTH, 1, 2 // Cura III
                    ),
                    new MobEffectInstance(
                            MobEffects.STRENGTH, 1200, 0 // Força por 1:00
                    ),
                    new MobEffectInstance(
                            MobEffects.SPEED, 1200, 0 // Velocidade por 1:00
                    )
            ))).build();

    // Foods
    public static final DeferredItem<BlockItem> VITAL_FRUIT = FOODS.registerSimpleBlockItem(
            "vital_fruit",
            InitBlocks.VITAL_BUSH,
            new Item.Properties().food(VITAL_FRUIT_PROP));

    public static final DeferredItem<Item> LAZULI_APPLE = FOODS.registerItem(
            "lazuli_apple", props -> new Item(props
                    .food(LAZULI_APPLE_PROP, LAZULI_APPLE_CONSUMABLE)
                    .rarity(Rarity.RARE)));
}