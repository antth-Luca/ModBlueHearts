package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.handlers.AttachmentsHandler;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;

@EventBusSubscriber(modid = BlueHearts.MODID)
public class BlueBloodWorks {
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                AttachmentsHandler.syncBlueBlood(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHurted(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);

            if (currentData.hasRemaining()) {
                float adjustedDamage = event.getAmount();
                float currentBlueBlood = currentData.getBlueBlood();

                if (adjustedDamage <= currentBlueBlood) {
                    AttachmentsHandler.setAndSyncBlueBlood(
                            player,
                            currentData.subBlueBlood(adjustedDamage)
                    );
                    event.setCanceled(true);
                } else {
                    AttachmentsHandler.setAndSyncBlueBlood(
                            player,
                            currentData.setBlueBlood(0)
                    );
                    event.setAmount(adjustedDamage - currentBlueBlood);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayersWakeUp(SleepFinishedTimeEvent event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.players().forEach((serverPlayer) -> {
                BlueBloodData currentData = serverPlayer.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);
                BlueBloodData newData = currentData.setBlueBlood(currentData.getMaxBlueBlood());

                serverPlayer.setData(InitAttachmentTypes.PLAYER_BLUE_BLOOD, newData);
                AttachmentsHandler.syncBlueBlood(serverPlayer);
            });
        }
    }
}
