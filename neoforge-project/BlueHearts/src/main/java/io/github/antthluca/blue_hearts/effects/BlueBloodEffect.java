package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BlueBloodEffect extends MobEffect {
    public BlueBloodEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5a82e2);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide && entity instanceof Player player) {
            player.setData(
                    InitAttachmentTypes.PLAYER_BLUE_BLOOD,
                    player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD)
                            .addMaxBlueBlood(amplifier + 1)
                            .addBlueBlood(amplifier + 1)
            );
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration > 0;
    }
}
