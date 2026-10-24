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
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.Event;
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
                event.addCapability(ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID, "properties"), new PlayerBlueBloodProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(BHCapabilities.PLAYER_BLUE_BLOOD).ifPresent(oldStore -> {
                event.getEntity().getCapability(BHCapabilities.PLAYER_BLUE_BLOOD).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
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
    public static void onPlayerHurted(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        CapabilitesHandler.getBlueBlood(player).ifPresent(current -> {
            if (!current.hasRemaining()) return;

            final float rawDamage = event.getAmount();

            // Petrified Blue Blood
            final float adjustedDamage = CurioItemsHandler.hasCurio(player, InitItems.PETRIFIED_BLUE_BLOOD.get())
                    ? CombatHandler.getDamageAfterPureAbsorb(
                    rawDamage,
                    PetrifiedBlueBlood.ARMOR,
                    PetrifiedBlueBlood.TOUGHNESS_ARMOR)
                    : rawDamage;

            final float currentBlueBlood = current.getBlueBlood();
            final DamageSource source = event.getSource();

            if (adjustedDamage < currentBlueBlood) {
                CapabilitesHandler.setAndSyncBlueBlood(player, bb -> bb.subBlueBlood(adjustedDamage));
                event.setCanceled(true);
            } else {
                CapabilitesHandler.setAndSyncBlueBlood(player, bb -> bb.setBlueBlood(0));

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
                Level level = player.level();
                level.playSound(
                        null,
                        player.getX(), player.getY(), player.getZ(),
                        hurtSound,
                        player.getSoundSource(),
                        1.0F,
                        (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F
                );
            }
        });
    }

    @SubscribeEvent
    public static void onPlayersWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();

        if (!(player.level().isClientSide() && event.isCanceled())) {
            CapabilitesHandler.setAndSyncBlueBlood(player, bb -> {
                bb.setBlueBlood(bb.getMaxBlueBlood());
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerEffectAdded(MobEffectEvent.Applicable event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            MobEffectInstance effectIntance = event.getEffectInstance();

            if (effectIntance.getEffect().equals(MobEffects.ABSORPTION)) {
                CapabilitesHandler.getBlueBlood(player).ifPresent(current -> {
                    if (current.isMaximum()) return;

                    float yellowHearts = (4 * (1 + effectIntance.getAmplifier())) / 2.0F;
                    float currentBlueBlood = current.getBlueBlood();
                    float maxBlueBlood = current.getMaxBlueBlood();

                    if (currentBlueBlood + yellowHearts > maxBlueBlood) {
                        player.setAbsorptionAmount((yellowHearts - (maxBlueBlood - currentBlueBlood)) / 2);
                        CapabilitesHandler.setAndSyncBlueBlood(player, bb -> {
                            bb.setBlueBlood(maxBlueBlood);
                        });
                    } else {
                        CapabilitesHandler.setAndSyncBlueBlood(player, bb -> {
                            bb.addBlueBlood(yellowHearts);
                        });
                    }
                    event.setResult(Event.Result.DENY);
                });
            }
        }
    }
}
