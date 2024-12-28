package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.potions.BlueBloodPotion;
import io.github.antthluca.blue_hearts.potions.OrageAntidotePotion;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS, BlueHearts.MODID
    );

    // Items
    public static final RegistryObject<Item> VITAL_SAP = ITEMS.register(
        "vital_sap", () -> new Item(new Item.Properties()));

    // Potion Items
    public static final RegistryObject<Item> BLUE_BLOOD_POTION = ITEMS.register(
        "blue_blood_potion", BlueBloodPotion::new);

    public static final RegistryObject<Item> ORANGE_ANTIDOTE_POTION = ITEMS.register(
        "orange_antidote_potion", OrageAntidotePotion::new);
}
