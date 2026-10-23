package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.datagen.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID)
public class BHDataGeneration {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        // Block Tags
        BHBlockTagsProvider blockTags = gen.addProvider(event.includeServer(),
            new BHBlockTagsProvider(out, lookup, helper));

        // Item Tags
        gen.addProvider(event.includeServer(), new BHItemTagsProvider(
            out,
            lookup,
            blockTags.contentsGetter(),
            helper
        ));

        // Advancements
        gen.addProvider(event.includeServer(), new BHAdvancementsProvider(
            out,
            lookup
        ));

        // World Generation
        gen.addProvider(event.includeServer(), new BHWorldGenProvider(
            out,
            lookup
        ));
    }
}
