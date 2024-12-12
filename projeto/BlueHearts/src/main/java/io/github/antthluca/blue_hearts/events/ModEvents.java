package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBlood;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import io.github.antthluca.blue_hearts.networking.ModMessages;
import io.github.antthluca.blue_hearts.networking.packet.BlueBloodDataSyncS2CPacket;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).isPresent()) {
                event.addCapability(new ResourceLocation(BlueHearts.MODID, "properties"), new PlayerBlueBloodProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerBlueBlood.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blue_blood -> {
                if (event.player.tickCount % 20 == 0) {  // A cada segundo
                    ModMessages.sendToPlayer(new BlueBloodDataSyncS2CPacket(blue_blood.getBlueBlood(), blue_blood.getMAXBlueBlood()), ((ServerPlayer) event.player));
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blue_blood -> {
                    ModMessages.sendToPlayer(new BlueBloodDataSyncS2CPacket(blue_blood.getBlueBlood(), blue_blood.getMAXBlueBlood()), player);
                });
            }
        }
    }
}
