package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.handlers.AttachmentsHandler;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BlueBloodEffect extends MobEffect {
    public BlueBloodEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5a82e2);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplifier) {
        if (mob instanceof Player player) {
            AttachmentsHandler.setAndSyncBlueBlood(
                    player,
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
