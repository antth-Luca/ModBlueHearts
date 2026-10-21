package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class BHRelicsWorks {
    // Heart of Marblemaroon
    @SubscribeEvent
    public static void onPlayerHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player player
                && CurioItemsHandler.hasCurio(player, InitItems.HEART_MARBLEMAROON.get())) {
            event.setAmount(0);
        }
    }

    @SubscribeEvent
    public static void onPlayerEffectAdded(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (CurioItemsHandler.hasCurio(player, InitItems.HEART_MARBLEMAROON.get())) {
                Holder<MobEffect> effect = event.getEffectInstance().getEffect();

                if (effect.is(MobEffects.REGENERATION)
                        || effect.is(MobEffects.SATURATION)
                        || effect.is(MobEffects.HUNGER)) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        }
    }
}