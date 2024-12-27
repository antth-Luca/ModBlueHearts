package io.github.antthluca.blue_hearts.integration.curios.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.integration.curios.effects.CryingCharmEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitEffectsCurios {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
        ForgeRegistries.MOB_EFFECTS, BlueHearts.MODID
    );

    // Effects
    public static final RegistryObject<MobEffect> CRYING_CHARM_EFFECT = MOB_EFFECTS.register(
        "crying_charm_effect", CryingCharmEffect::new);
}
