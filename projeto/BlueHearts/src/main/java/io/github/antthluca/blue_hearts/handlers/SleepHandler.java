package io.github.antthluca.blue_hearts.handlers;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID)
public class SleepHandler {
    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (!event.isCanceled() && !player.level.isClientSide()) { // Garante que é no lado do servidor
            player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blueBlood -> {
                float maxBlueBlood = blueBlood.getMAXBlueBlood();
                blueBlood.setBlueBlood(maxBlueBlood); // Restaura até o máximo
            });
        }
    }
}
