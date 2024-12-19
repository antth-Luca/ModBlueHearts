package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.foods.CustomFoods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFoods {
    public static final DeferredRegister<Item> FOOD_ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS, BlueHearts.MODID
    );

    // Foods
    public static final RegistryObject<Item> VITAL_FRUIT = FOOD_ITEMS.register(
        "vital_fruit", () -> new ItemNameBlockItem(
            InitBlocks.VITAL_BUSH.get(), 
            new Item.Properties()
                .food(CustomFoods.VITAL_FRUIT_FOOD)
                .tab(CreativeModeTab.TAB_FOOD)));

    public static final RegistryObject<Item> LAZULI_APPLE = FOOD_ITEMS.register(
        "lazuli_apple", () -> new Item(new Item.Properties()
            .food(CustomFoods.LAZULI_APPLE_FOOD)
            .rarity(Rarity.RARE)
            .tab(CreativeModeTab.TAB_FOOD)));
}
