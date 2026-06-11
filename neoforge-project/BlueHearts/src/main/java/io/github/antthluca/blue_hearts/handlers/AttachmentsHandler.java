package io.github.antthluca.blue_hearts.handlers;

import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.networking.packets.BlueBloodDataSyncPayload;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.Supplier;

public class AttachmentsHandler {
    public static void setAndSyncBlueBlood(Player player, BlueBloodData data) {
        player.setData(
                InitAttachmentTypes.PLAYER_BLUE_BLOOD,
                data
        );

        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new BlueBloodDataSyncPayload(data));
        }
    }
}
