package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = BlueHearts.MODID)
public class BlueBloodWorks {
    @SubscribeEvent
    public static void onPlayerHurted(LivingEvent) {

    }
}
