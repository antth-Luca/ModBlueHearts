package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.datagen.BHBlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = BlueHearts.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BHDataGeneration {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        var lookup = event.getLookupProvider();

        gen.addProvider(true, new BHBlockTagsProvider(
                out,
                lookup,
                BlueHearts.MODID,
                event.getExistingFileHelper()
        ));
    }
}
