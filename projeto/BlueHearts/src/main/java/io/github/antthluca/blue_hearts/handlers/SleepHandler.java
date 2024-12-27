package io.github.antthluca.blue_hearts.handlers;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID)
public class SleepHandler {
    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        
        // Verifica se não é cancelado, está no lado do servidor, e o jogador realmente dormiu por uma noite
        if (!event.isCanceled() && !player.level.isClientSide() && player.level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel) player.level;

            // Checa se o mundo está no início do dia (geralmente quando o tempo é 0)
            if (serverLevel.getDayTime() % 24000L == 0) {
                player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blueBlood -> {
                    float maxBlueBlood = blueBlood.getMAXBlueBlood();
                    blueBlood.setBlueBlood(maxBlueBlood); // Restaura até o máximo
                });
            }
        }
    }
}
