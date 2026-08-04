package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.datagen.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = BlueHearts.MODID)
public class BHDataGeneration {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        var lookup = event.getLookupProvider();

        // Block Tags
        gen.addProvider(true, new BHBlockTagsProvider(
                out,
                lookup,
                BlueHearts.MODID
        ));

        // Item Tags
        gen.addProvider(true, new BHItemTagsProvider(
                out,
                lookup,
                BlueHearts.MODID
        ));

        // Advancements
        gen.addProvider(true, new BHAdvancementsProvider(
                out,
                lookup
        ));

        // World Generation
        gen.addProvider(true, new BHWorldGenProvider(
                out,
                lookup
        ));

        // Curios Slots
        gen.addProvider(true, new BHCurioSlotsProvider(
                out,
                lookup
        ));
    }
}
