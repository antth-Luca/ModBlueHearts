package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.datagen.BHAdvancementsProvider;
import io.github.antthluca.blue_hearts.datagen.BHBlockTagsProvider;
import io.github.antthluca.blue_hearts.datagen.BHItemTagsProvider;
import io.github.antthluca.blue_hearts.datagen.BHWorldGenProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = BlueHearts.MODID)
public class BHDataGeneration {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        var lookup = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();

        // Block Tags
        BHBlockTagsProvider modBlockTagsProvider = new BHBlockTagsProvider(
            out,
            lookup,
            BlueHearts.MODID,
            event.getExistingFileHelper()
        );
        gen.addProvider(true, modBlockTagsProvider);

        // Item Tags
        gen.addProvider(true, new BHItemTagsProvider(
                out,
                lookup,
                modBlockTagsProvider.contentsGetter(),
                BlueHearts.MODID,
                existingFileHelper
        ));

        // Advancements
        gen.addProvider(event.includeServer(), new BHAdvancementsProvider(
                out,
                lookup,
                existingFileHelper
        ));

        // World Generation
        gen.addProvider(event.includeServer(), new BHWorldGenProvider(
                out,
                lookup
        ));
    }
}
