package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitFoods;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ConsumeItemTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BHAdvancementsProvider extends AdvancementProvider {
    public BHAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new BHAdvancementsGenerator()));
    }

    private static final class BHAdvancementsGenerator implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
            // Acquire Vital Fruit
            AdvancementHolder acquireVitalFruit = Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("minecraft:adventure/root"))
                    .display(
                            new ItemStackTemplate(InitFoods.VITAL_FRUIT.get()),
                            Component.translatable("advancement.blue_hearts.acquire_vital_fruit.title"),
                            Component.translatable("advancement.blue_hearts.acquire_vital_fruit.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("acquire_vital_fruit", InventoryChangeTrigger.TriggerInstance.hasItems(InitFoods.VITAL_FRUIT.get()))
                    .save(
                            saver,
                            Identifier.fromNamespaceAndPath(BlueHearts.MODID, "acquire_vital_fruit")
                    );

            // Eat Lazuli Apple
            AdvancementHolder eatVitalFruit = Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("minecraft:husbandry/root"))
                    .display(
                            new ItemStackTemplate(InitFoods.LAZULI_APPLE.get()),
                            Component.translatable("advancement.blue_hearts.eat_lazuli_apple.title"),
                            Component.translatable("advancement.blue_hearts.eat_lazuli_apple.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("eat_vital_fruit", ConsumeItemTrigger.TriggerInstance.usedItem(registries.lookupOrThrow(Registries.ITEM), InitFoods.LAZULI_APPLE.get()))
                    .save(
                            saver,
                            Identifier.fromNamespaceAndPath(BlueHearts.MODID, "eat_lazuli_apple")
                    );
        }
    }
}
