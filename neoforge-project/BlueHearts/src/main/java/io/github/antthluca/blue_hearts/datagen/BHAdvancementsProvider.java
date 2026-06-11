package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitFoods;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BHAdvancementsProvider extends AdvancementProvider {
    public BHAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of());
    }

    private static class BHAdvancementsGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            // Acquire Vital Fruit
            AdvancementHolder acquireVitalFruit = Advancement.Builder.advancement()
                    .parent(ResourceLocation.withDefaultNamespace("adventure/root"))
                    .display(
                            new ItemStack(InitFoods.VITAL_FRUIT.get()),
                            Component.translatable("advancement.blue_hearts.acquire_vital_fruit.title"),
                            Component.translatable("advancement.blue_hearts.acquire_vital_fruit.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("acquire_vital_fruit", InventoryChangeTrigger.TriggerInstance.hasItems(InitFoods.VITAL_FRUIT.get()))
                    .save(
                            saver,
                            ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID, "acquire_vital_fruit"),
                            existingFileHelper
                    );

            // Eat Lazuli Apple
            AdvancementHolder eatVitalFruit = Advancement.Builder.advancement()
                    .parent(ResourceLocation.withDefaultNamespace("husbandry/root"))
                    .display(
                            new ItemStack(InitFoods.LAZULI_APPLE.get()),
                            Component.translatable("advancement.blue_hearts.eat_lazuli_apple.title"),
                            Component.translatable("advancement.blue_hearts.eat_lazuli_apple.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("", ConsumeItemTrigger.TriggerInstance.usedItem(InitFoods.LAZULI_APPLE.get()))
                    .save(
                            saver,
                            ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID, "eat_lazuli_apple"),
                            existingFileHelper
                    );
        }
    }
}
