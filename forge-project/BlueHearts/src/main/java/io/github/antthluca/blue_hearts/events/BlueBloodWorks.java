package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.capabilities.BHCapabilities;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBlood;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
import io.github.antthluca.blue_hearts.handlers.CombatHandler;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import io.github.antthluca.blue_hearts.items.relics.custom.PetrifiedBlueBlood;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID)
public class BlueBloodWorks {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerBlueBlood.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(BHCapabilities.PLAYER_BLUE_BLOOD).isPresent()) {
                event.addCapability(new ResourceLocation(BlueHearts.MODID, "properties"), new PlayerBlueBloodProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                CapabilitesHandler.syncBlueBlood(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHurted(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.level().isClientSide()) return;

            BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);

            if (currentData.hasRemaining()) {

                // Damage
                float rawDamage = event.getAmount();
                float adjustedDamage = rawDamage;
                // Petrified Blue Blood
                if (CurioItemsHandler.hasCurio(player, InitItems.PETRIFIED_BLUE_BLOOD.get())) {
                    adjustedDamage = CombatHandler.getDamageAfterPureAbsorb(
                            rawDamage,
                            PetrifiedBlueBlood.ARMOR,
                            PetrifiedBlueBlood.TOUGHNESS_ARMOR
                    );
                }

                float currentBlueBlood = currentData.getBlueBlood();
                DamageSource source = event.getSource();

                if (adjustedDamage < currentBlueBlood) {
                    CapabilitesHandler.setAndSyncBlueBlood(
                            player,
                            currentData.subBlueBlood(adjustedDamage)
                    );
                    event.setCanceled(true);
                } else {
                    CapabilitesHandler.setAndSyncBlueBlood(
                            player,
                            currentData.setBlueBlood(0)
                    );

                    // Heart of Marblemaroon
                    if (CurioItemsHandler.hasCurio(player, InitItems.HEART_MARBLEMAROON.get())) {
                        player.hurt(source, Float.MAX_VALUE);
                        CurioItemsHandler.removeCurio(player, InitItems.HEART_MARBLEMAROON.get());
                        event.setCanceled(true);
                        return;
                    } else if (adjustedDamage == currentBlueBlood) {
                        event.setCanceled(true);
                    } else {
                        event.setAmount(adjustedDamage - currentBlueBlood);
                    }
                }

                // Sound
                SoundEvent hurtSound = source.type().effects().sound();

                if (hurtSound != null) {
                    Level playerLevel = player.level();
                    playerLevel.playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            hurtSound,
                            player.getSoundSource(),
                            1.0F,
                            (playerLevel.getRandom().nextFloat()
                                    - playerLevel.getRandom().nextFloat()) * 0.2F + 1.0F
                    );
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
                CapabilitesHandler.syncBlueBlood(serverPlayer);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerEffectAdded(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            MobEffectInstance effectIntance = event.getEffectInstance();

            if (effectIntance.getEffect().is(MobEffects.ABSORPTION)) {
                BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);
                if (currentData.isMaximum()) return;

                float yellowHearts = (4 * (1 + effectIntance.getAmplifier())) / 2.0F;
                float currentBlueBlood = currentData.getBlueBlood();
                float maxBlueBlood = currentData.getMaxBlueBlood();

                if (currentBlueBlood + yellowHearts > maxBlueBlood) {
                    player.setAbsorptionAmount((yellowHearts - (maxBlueBlood - currentBlueBlood)) / 2);
                    CapabilitesHandler.setAndSyncBlueBlood(
                            player,
                            currentData.setBlueBlood(maxBlueBlood)
                    );
                } else {
                    CapabilitesHandler.setAndSyncBlueBlood(
                            player,
                            currentData.addBlueBlood(yellowHearts)
                    );
                }
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }
}
