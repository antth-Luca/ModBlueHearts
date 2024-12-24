package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.world.features.VitalBushFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(
        ForgeRegistries.FEATURES, BlueHearts.MODID
    );

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> VITAL_BUSH_FEATURE = FEATURES.register(
        "vital_bush", () -> new VitalBushFeature(
            NoneFeatureConfiguration.CODEC,
            InitBlocks.VITAL_BUSH.getHolder().get()));
}
