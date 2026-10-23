package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitFoods;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class BHAdvancementsProvider extends ForgeAdvancementProvider {
    public BHAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new BHAdvancementsGenerator()));
    }

    private static final class BHAdvancementsGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper helper) {
            // Acquire Vital Fruit
            Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("minecraft:adventure/root"))
                    .display(
                        new ItemStack(InitFoods.VITAL_FRUIT.get()),
                        Component.translatable("advancement.blue_hearts.acquire_vital_fruit.title"),
                        Component.translatable("advancement.blue_hearts.acquire_vital_fruit.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                    )
                    .addCriterion("acquire_vital_fruit", InventoryChangeTrigger.TriggerInstance.hasItems(InitFoods.VITAL_FRUIT.get()))
                    .save(
                        saver,
                        ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID, "acquire_vital_fruit"),
                        helper
                    );

            // Eat Lazuli Apple
            Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("minecraft:husbandry/root"))
                    .display(
                        new ItemStack(InitFoods.LAZULI_APPLE.get()),
                        Component.translatable("advancement.blue_hearts.eat_lazuli_apple.title"),
                        Component.translatable("advancement.blue_hearts.eat_lazuli_apple.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                    )
                    .addCriterion("eat_vital_fruit", ConsumeItemTrigger.TriggerInstance.usedItem(InitFoods.LAZULI_APPLE.get()))
                    .save(
                        saver,
                        ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID, "eat_lazuli_apple"),
                        helper
                    );
        }
    }
}
