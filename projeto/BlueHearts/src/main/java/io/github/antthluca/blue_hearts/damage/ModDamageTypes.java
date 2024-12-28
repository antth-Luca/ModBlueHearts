package io.github.antthluca.blue_hearts.damage;


import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageTypes {
    ResourceKey<DamageType> MAGIC = ResourceKey.create(
        Registries.DAMAGE_TYPE,
        new ResourceLocation(BlueHearts.MODID, "magic")
    );
}
