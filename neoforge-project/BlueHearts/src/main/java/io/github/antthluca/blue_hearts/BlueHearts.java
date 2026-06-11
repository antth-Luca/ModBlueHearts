package io.github.antthluca.blue_hearts;

import io.github.antthluca.blue_hearts.config.BHCommonConfig;
import io.github.antthluca.blue_hearts.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(BlueHearts.MODID)
public class BlueHearts {
    public static final String MODID = "blue_hearts";

    public BlueHearts(IEventBus bus, ModContainer container) {
        // Init
        InitAttachmentTypes.TYPES.register(bus);
        InitEffects.MOB_EFFECTS.register(bus);
        InitFoods.FOODS.register(bus);
        InitItems.ITEMS.register(bus);
        InitBlocks.BLOCKS.register(bus);
        InitCreativeTabs.TABS.register(bus);

        // Config
        container.registerConfig(ModConfig.Type.COMMON, BHCommonConfig.SPEC, "bluehearts-common.toml");
    }
}
