package io.github.antthluca.blue_hearts;

import io.github.antthluca.blue_hearts.config.BHCommonConfig;
import io.github.antthluca.blue_hearts.init.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(BlueHearts.MODID)
public class BlueHearts {
    public static final String MODID = "blue_hearts";

    public BlueHearts(IEventBus bus, ModContainer container) {
        // Init
        InitEffects.MOB_EFFECTS.register(bus);
        InitFoods.FOODS.register(bus);
        InitItems.ITEMS.register(bus);
        InitBlocks.BLOCKS.register(bus);
        InitCreativeTabs.TABS.register(bus);

        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BHCommonConfig.SPEC, "bluehearts-common.toml");
    }
}
