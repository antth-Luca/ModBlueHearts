package io.github.antthluca.blue_hearts.effects;

import io.github.antthluca.blue_hearts.init.InitEffects;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CryingCharmEffect extends MobEffect {
    public CryingCharmEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF0055);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplifier) {
        if (mob instanceof Player player) {
            if (player.getHealth() > 1.0f) {
                DamageSource magic = player.level().damageSources().magic();
                player.hurt(magic, 1.0f);
            } else {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);

                    if (stack.getItem() == InitItems.MYST_BLUE_GEM.get()) {
                        player.getInventory().setItem(i, new ItemStack(InitItems.CRYING_CHARM.get()));
                        break;
                    }
                }

                // Remove o efeito após a troca
                player.removeEffect(InitEffects.CRYING_CHARM);
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
