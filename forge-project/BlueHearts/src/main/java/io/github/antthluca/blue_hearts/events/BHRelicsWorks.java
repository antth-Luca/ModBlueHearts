package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
                MobEffect effect = event.getEffectInstance().getEffect();

                if (effect.equals(MobEffects.REGENERATION)
                    || effect.equals(MobEffects.SATURATION)
                    || effect.equals(MobEffects.HUNGER)) {
                        event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}