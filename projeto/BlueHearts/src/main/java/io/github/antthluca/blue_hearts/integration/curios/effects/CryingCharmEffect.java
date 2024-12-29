package io.github.antthluca.blue_hearts.integration.curios.effects;

import io.github.antthluca.blue_hearts.damage.ModDamageSources;
import io.github.antthluca.blue_hearts.integration.curios.init.InitItemsCurios;
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
    public void applyEffectTick(@SuppressWarnings("null") LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            if (player.getHealth() > 1.0f) {
                DamageSource magic = new ModDamageSources(player.level().registryAccess()).magic();
                player.hurt(magic, 1.0f);
            } else {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);

                    if (stack.getItem() == InitItemsCurios.MYST_BLUE_GEM.get()) {
                        player.getInventory().setItem(i, new ItemStack(InitItemsCurios.CRYING_CHARM.get()));
                        break;
                    }
                }

                // Remove o efeito após a troca
                player.removeEffect(this);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Aplica o efeito a cada tick
        return true;
    }
}
