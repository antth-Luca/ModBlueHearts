package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.blocks.custom.VitalBushBlock;
import io.github.antthluca.blue_hearts.init.InitBlocks;
import io.github.antthluca.blue_hearts.init.InitFoods;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class BHLootTableProvider extends LootTableProvider {
    public BHLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(BHBlockLoot::new, LootContextParamSets.BLOCK)));
    }

    public static final class BHBlockLoot extends BlockLootSubProvider {
        public BHBlockLoot() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            this.add(InitBlocks.VITAL_BUSH.get(), block -> LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(InitFoods.VITAL_FRUIT.get())
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(VitalBushBlock.AGE, 3))))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return InitBlocks.BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .toList();
        }
    }
}
