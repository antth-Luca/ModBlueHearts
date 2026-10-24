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

@Mod.EventBusSubscriber(modid = BlueHearts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BHDataGeneration {
    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent event) {
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
            lookup,
            helper
        ));

        // World Generation
        gen.addProvider(event.includeServer(), new BHWorldGenProvider(
            out,
            lookup
        ));

        // Loot Tables
        gen.addProvider(event.includeServer(), new BHLootTableProvider(out));

        // Recipes
        gen.addProvider(event.includeServer(), new BHRecipesProvider(out));

        // Curios Slots
        gen.addProvider(event.includeServer(), new BHCurioSlotsProvider(
            out,
            lookup,
            helper
        ));
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        // Models
        gen.addProvider(event.includeClient(), new BHModelsProvider(
            out,
            helper
        ));

        // Block States
        gen.addProvider(event.includeClient(), new BHBlockStateProvider(
            out,
            helper
        ));

        // Languages
        gen.addProvider(event.includeClient(), new BHLanguageProvider(out));
    }
}
