package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.items.custom.MystBlueGem;
import io.github.antthluca.blue_hearts.items.potions.custom.BlueBloodPotion;
import io.github.antthluca.blue_hearts.items.potions.custom.OrangeAntidotePotion;
import io.github.antthluca.blue_hearts.items.relics.custom.CryingCharm;
import io.github.antthluca.blue_hearts.items.relics.custom.HeartMarblemaroon;
import io.github.antthluca.blue_hearts.items.relics.custom.PetrifiedBlueBlood;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS, BlueHearts.MODID);

    // Items
    public static final RegistryObject<Item> VITAL_SAP = ITEMS.register(
        "vital_sap", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLUE_EYE = ITEMS.register(
        "blue_eye", () -> new Item(new Item.Properties()
                .stacksTo(16)));

    public static final RegistryObject<Item> MYST_BLUE_GEM = ITEMS.register(
        "myst_blue_gem", MystBlueGem::new);

    // Relics
    public static final RegistryObject<Item> CRYING_CHARM = ITEMS.register(
        "crying_charm", CryingCharm::new);

    public static final RegistryObject<Item> PETRIFIED_BLUE_BLOOD = ITEMS.register(
        "petrified_blue_blood", PetrifiedBlueBlood::new);

    public static final RegistryObject<Item> HEART_MARBLEMAROON = ITEMS.register(
        "heart_marblemaroon", HeartMarblemaroon::new);

    // Potions
    public static final RegistryObject<Item> BLUE_BLOOD_POTION = ITEMS.register(
        "blue_blood_potion", BlueBloodPotion::new);

    public static final RegistryObject<Item> ORANGE_ANTIDOTE_POTION = ITEMS.register(
        "orange_antidote_potion", OrangeAntidotePotion::new);
}
