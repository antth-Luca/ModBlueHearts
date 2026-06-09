package io.github.antthluca.blue_hearts;

import com.mojang.logging.LogUtils;
import io.github.antthluca.blue_hearts.init.InitBlocks;
import io.github.antthluca.blue_hearts.init.InitEffects;
import io.github.antthluca.blue_hearts.init.InitFoods;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(BlueHearts.MODID)
public class BlueHearts {
    public static final String MODID = "blue_hearts";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BlueHearts(IEventBus bus, ModContainer container) {
        // Init
        InitBlocks.BLOCKS.register(bus);
        InitFoods.FOODS.register(bus);
        InitItems.ITEMS.register(bus);
        InitEffects.MOB_EFFECTS.register(bus);
    }
}
