package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.effects.BlueBloodEffect;
import io.github.antthluca.blue_hearts.effects.CryingCharmEffect;
import io.github.antthluca.blue_hearts.effects.OrangeAntidoteEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            Registries.MOB_EFFECT, BlueHearts.MODID);

    // Mob Effects
    public static final DeferredHolder<MobEffect, MobEffect> BLUE_BLOOD = MOB_EFFECTS.register(
            "blue_blood", BlueBloodEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> ORANGE_ANTIDOTE = MOB_EFFECTS.register(
            "orange_antidote", OrangeAntidoteEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> CRYING_CHARM = MOB_EFFECTS.register(
            "crying_charm", CryingCharmEffect::new);
}
