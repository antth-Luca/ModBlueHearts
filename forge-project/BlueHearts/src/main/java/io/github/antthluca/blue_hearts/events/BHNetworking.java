package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.networking.packets.BlueBloodDataSyncPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = BlueHearts.MODID)
public class BHNetworking {
    @SubscribeEvent
    public static void registerNetworking(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(BlueHearts.MODID).versioned("1.0");

        registrar.playToClient(
                BlueBloodDataSyncPayload.TYPE,
                BlueBloodDataSyncPayload.STREAM_CODEC,
                (pyl, ctx) -> {
                    ctx.enqueueWork(() -> {
                        var player = Minecraft.getInstance().player;
                        if (player != null) {
                            player.setData(
                                    InitAttachmentTypes.PLAYER_BLUE_BLOOD,
                                    pyl.data()
                            );
                        }
                    });
                }
        );
    }
}
