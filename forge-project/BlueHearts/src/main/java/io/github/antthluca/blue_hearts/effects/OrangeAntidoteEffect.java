package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
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
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            CapabilitesHandler.getBlueBlood(player).ifPresent(bb -> {
                float current = bb.getBlueBlood();

                if (current == 0) {  // Se o player não tiver Blue Blood, recebe dano
                    DamageSource source = player.level().damageSources().magic();
                    player.hurt(source, 19.0F);  // Dano de 9.5 corações
                } else {  // Se tiver, reduz para zero
                    CapabilitesHandler.setAndSyncBlueBlood(player, consumer -> {
                        consumer.setMAXBlueBlood(0);
                        consumer.setBlueBlood(0);
                    });
                }
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
