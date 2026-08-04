package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.blocks.custom.VitalBushBlock;
import io.github.antthluca.blue_hearts.init.InitBlocks;
import io.github.antthluca.blue_hearts.tags.BHTags;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BHWorldGenProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, BHWorldGenProvider::bootstrapConfigured)
            .add(Registries.PLACED_FEATURE, BHWorldGenProvider::bootstrapPlaced)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BHWorldGenProvider::bootstrapModifier);

    public static final Identifier vitalBushKey = Identifier.fromNamespaceAndPath(
        BlueHearts.MODID, "vital_bush"
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> VITAL_BUSH_CONFIGURED =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    vitalBushKey
    );
    public static final ResourceKey<PlacedFeature> VITAL_BUSH_PLACED =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    vitalBushKey
    );
    public static final ResourceKey<BiomeModifier> VITAL_BUSH_BIOME_MODIFIER =
            ResourceKey.create(
                    NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                    vitalBushKey
    );

    public BHWorldGenProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> registries) {
        super(out, registries, BUILDER, Set.of(BlueHearts.MODID));
    }

    // Configured Features
    public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
        BlockState baseState = InitBlocks.VITAL_BUSH.get().defaultBlockState();
        WeightedList.Builder<BlockState> stateList = WeightedList.builder();
        stateList.add(baseState.setValue(VitalBushBlock.AGE, 0), 35);
        stateList.add(baseState.setValue(VitalBushBlock.AGE, 1), 35);
        stateList.add(baseState.setValue(VitalBushBlock.AGE, 2), 25);
        stateList.add(baseState.setValue(VitalBushBlock.AGE, 3), 5);

        ctx.register(VITAL_BUSH_CONFIGURED, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(stateList))
        ));
    }

    // Placed Features
    public static void bootstrapPlaced(BootstrapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);

        ctx.register(VITAL_BUSH_PLACED, new PlacedFeature(
           configuredFeatures.getOrThrow(VITAL_BUSH_CONFIGURED),
                List.of(
                        CountPlacement.of(256),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.aboveBottom(0),
                                VerticalAnchor.absolute(256)
                        ),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.allOf(
                                        BlockPredicate.anyOf(
                                            BlockPredicate.matchesBlocks(Blocks.AIR),
                                            BlockPredicate.matchesBlocks(Blocks.CAVE_AIR)
                                    ),
                                    BlockPredicate.matchesTag(
                                            new Vec3i(0, 1, 0),
                                            BHTags.VALID_BLOCKS_FOR_VITAL_BUSH
                                    )
                                )
                        ),
                        BiomeFilter.biome()
                )
        ));
    }

    // Biome Modifiers
    public static void bootstrapModifier(BootstrapContext<BiomeModifier> ctx) {
        HolderGetter<PlacedFeature> placedFeatures = ctx.lookup(Registries.PLACED_FEATURE);
        HolderGetter<net.minecraft.world.level.biome.Biome> biomes = ctx.lookup(Registries.BIOME);

        ctx.register(VITAL_BUSH_BIOME_MODIFIER, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.LUSH_CAVES)),
                HolderSet.direct(placedFeatures.getOrThrow(VITAL_BUSH_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }
}
