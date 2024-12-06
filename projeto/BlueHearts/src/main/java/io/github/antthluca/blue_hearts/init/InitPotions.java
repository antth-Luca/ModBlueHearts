package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(
        ForgeRegistries.POTIONS, BlueHearts.MODID
    );

    // Potions
    public static final RegistryObject<Potion> BLUE_BLOOD_POTION = POTIONS.register(
        "blue_blood_potion", () -> new Potion(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1)));
}
