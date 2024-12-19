package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OrangeAntidoteEffect extends MobEffect {
    public OrangeAntidoteEffect() {
        super(MobEffectCategory.HARMFUL, 0xe7813d);
    }

    @Override
    public void applyEffectTick(@SuppressWarnings("null") LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide && entity instanceof Player player) {
            player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blue_blood -> {
                float currentBlueBlood = blue_blood.getBlueBlood();

                if (currentBlueBlood == 0) {  // Se o player não tiver Blue Blood, recebe dano
                    player.hurt(DamageSource.MAGIC, 19f);  // Dano de 9.5 corações
                } else {  // Se tiver, reduz para zero
                    blue_blood.setMAXBlueBlood(0);
                    blue_blood.setBlueBlood(0);
                }
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Retorna true para aplicar o efeito no primeiro tick
        return duration > 0;
    }
}
