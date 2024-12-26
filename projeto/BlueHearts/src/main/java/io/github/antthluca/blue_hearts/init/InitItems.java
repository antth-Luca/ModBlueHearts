package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.world.item.CreativeModeTab;
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
        "vital_sap", () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_BREWING)));

    public static final RegistryObject<Item> REVITALIZING_CRYING_CHARM = ITEMS.register(
        "revitalizing_crying_charm", () -> new Item(new Item.Properties()
            .tab(CreativeModeTab.TAB_BREWING)));
}
