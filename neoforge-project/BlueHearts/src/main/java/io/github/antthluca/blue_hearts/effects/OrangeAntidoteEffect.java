package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
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
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide && entity instanceof Player player) {
            BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);
            if (currentData.getBlueBlood() == 0) {  // Se o player não tiver Blue Blood, recebe dano
                DamageSource magic = entity.level().damageSources().magic();
                player.hurt(magic, 19f);  // Dano de 9.5 corações
            } else {  // Se tiver, reduz para zero
                player.setData(
                        InitAttachmentTypes.PLAYER_BLUE_BLOOD,
                        currentData
                                .setMaxBlueBlood(0)
                                .setBlueBlood(0)
                );
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration > 0;
    }
}
