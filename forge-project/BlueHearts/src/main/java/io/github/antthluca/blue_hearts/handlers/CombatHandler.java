package io.github.antthluca.blue_hearts.handlers;

import net.minecraft.util.Mth;

public class CombatHandler {
    public static float getDamageAfterPureAbsorb(float damage, float armor, float armorToughness) {
        float f = 2.0F + armorToughness / 4.0F;
        float f1 = Mth.clamp(armor - damage / f, armor * 0.2F, 20.0F);
        float reduction = f1 / 25.0F;

        return damage * (1.0F - reduction);
    }
}
