package io.github.antthluca.blue_hearts.handlers;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID)
public class DamageHandler {
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        // Verifica se a entidade é um jogador
        if (event.getEntity() instanceof Player player) {
            player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blueBlood -> {
                float currentBlueBlood = blueBlood.getBlueBlood();

                // Verifica se o jogador tem "blue_blood" restante
                if (currentBlueBlood > 0) {
                    float adjustedDamage = event.getAmount(); // Dano que seria aplicado

                    if (adjustedDamage <= currentBlueBlood) {
                        // O dano é completamente absorvido pelo "blue_blood"
                        blueBlood.setBlueBlood(currentBlueBlood - adjustedDamage);
                        event.setCanceled(true); // Cancela o dano padrão do Minecraft
                    } else {
                        // Parte do dano vai para o "blue_blood" e o restante é aplicado normalmente
                        blueBlood.setBlueBlood(0); // "blue_blood" esgotado
                        event.setAmount(adjustedDamage - currentBlueBlood); // Ajusta o dano restante
                    }
                }
            });
        }
    }
}
