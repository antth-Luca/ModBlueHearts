package io.github.antthluca.blue_hearts.handlers;

import io.github.antthluca.blue_hearts.capabilities.BHCapabilities;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBlood;
import io.github.antthluca.blue_hearts.config.BHCommonConfig;
import io.github.antthluca.blue_hearts.networking.BHMessages;
import io.github.antthluca.blue_hearts.networking.packets.BlueBloodDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;

public class CapabilitesHandler {
    public static PlayerBlueBlood createPlayerBlueBlood() {
        PlayerBlueBlood data = new PlayerBlueBlood();
        int initialHearts = BHCommonConfig.INITIAL_BLUE_HEARTS_PER_PLAYER.get();

        data.setMaxBlueBlood(initialHearts);
        data.setBlueBlood(initialHearts);

        return data;
    }

    public static LazyOptional<PlayerBlueBlood> getBlueBlood(Player player) {
        return player.getCapability(BHCapabilities.PLAYER_BLUE_BLOOD);
    }

    public static void setAndSyncBlueBlood(Player player, NonNullConsumer<? super PlayerBlueBlood> consumer) {
        getBlueBlood(player).ifPresent(bb -> {
            consumer.accept(bb);

            if (player instanceof ServerPlayer serverPlayer) {
                BHMessages.sendToPlayer(new BlueBloodDataSyncS2CPacket(bb), serverPlayer);
            }
        });
    }

    public static void syncBlueBlood(ServerPlayer serverPlayer) {
        getBlueBlood(serverPlayer).ifPresent(bb -> {
            BHMessages.sendToPlayer(
                new BlueBloodDataSyncS2CPacket(bb),
                serverPlayer
            );
        });
    }
}
