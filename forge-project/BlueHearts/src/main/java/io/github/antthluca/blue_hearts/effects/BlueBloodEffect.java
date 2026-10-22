package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
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
        if (entity instanceof Player player) {
            CapabilitesHandler.setAndSyncBlueBlood(player, bb -> {
                bb.addMAXBlueBlood(amplifier + 1);
                bb.addBlueBlood(amplifier + 1);
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
