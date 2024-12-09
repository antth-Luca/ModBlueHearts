package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.effects.BlueBloodEffect;
import io.github.antthluca.blue_hearts.effects.OrangeAntidoteEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
        ForgeRegistries.MOB_EFFECTS, BlueHearts.MODID
    );

    // Effects
    public static final RegistryObject<MobEffect> BLUE_BLOOD = MOB_EFFECTS.register(
        "blue_blood", BlueBloodEffect::new);

    public static final RegistryObject<MobEffect> ORANGE_ANTIDOTE = MOB_EFFECTS.register(
        "orange_antidote", OrangeAntidoteEffect::new);
}
