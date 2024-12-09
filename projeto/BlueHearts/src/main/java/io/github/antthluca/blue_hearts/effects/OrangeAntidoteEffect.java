package io.github.antthluca.blue_hearts.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class OrangeAntidoteEffect extends MobEffect {
    public OrangeAntidoteEffect() {
        super(MobEffectCategory.HARMFUL, 0xe7813d);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Lógica do efeito "instantâneo"
        if (!entity.level.isClientSide) {
            System.out.println(">>> Remoção de " + amplifier + " corações azuis.");
            // Adicione aqui a lógica que altera os corações, por exemplo
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Retorna true para aplicar o efeito no primeiro tick
        return duration > 0;
    }
}
