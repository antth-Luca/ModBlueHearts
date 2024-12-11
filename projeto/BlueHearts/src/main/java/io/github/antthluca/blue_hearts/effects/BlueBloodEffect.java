package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BlueBloodEffect extends MobEffect {
    public BlueBloodEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5a82e2);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide && entity instanceof Player player) {
            player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blue_blood -> {
                blue_blood.addMAXBlueBlood(amplifier);
                blue_blood.addBlueBlood(amplifier);
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Retorna true para aplicar o efeito no primeiro tick
        return duration > 0;
    }
}
