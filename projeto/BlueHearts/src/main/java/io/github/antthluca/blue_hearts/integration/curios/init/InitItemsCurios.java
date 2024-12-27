package io.github.antthluca.blue_hearts.integration.curios.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.integration.curios.items.CryingCharm;
import io.github.antthluca.blue_hearts.integration.curios.items.MystBlueGem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItemsCurios {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS, BlueHearts.MODID
    );

    // Items
    public static final RegistryObject<Item> CRYING_CHARM = ITEMS.register(
            "crying_charm", () -> new CryingCharm(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .tab(CreativeModeTab.TAB_BREWING)));

    public static final RegistryObject<Item> BLUE_EYE = ITEMS.register(
        "blue_eye", () -> new Item(new Item.Properties()
            .stacksTo(16)
            .tab(CreativeModeTab.TAB_BREWING)));

    public static final RegistryObject<Item> MYST_BLUE_GEM = ITEMS.register(
        "myst_blue_gem", () -> new MystBlueGem(new Item.Properties()
            .stacksTo(1)
            .tab(CreativeModeTab.TAB_BREWING)));
}
