package io.github.antthluca.blue_hearts;

import io.github.antthluca.blue_hearts.init.InitItems;
import io.github.antthluca.blue_hearts.init.InitPotions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlueHearts.MODID)
public class BlueHearts {
    public static final String MODID = "blue_hearts";

    public BlueHearts() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        InitItems.ITEMS.register(bus);
        InitPotions.POTIONS.register(bus);
    }
}
