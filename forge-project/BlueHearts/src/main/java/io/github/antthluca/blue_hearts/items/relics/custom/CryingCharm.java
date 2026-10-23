package io.github.antthluca.blue_hearts.items.relics.custom;

import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class CryingCharm extends Item implements ICurioItem {
    public CryingCharm() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(itemStack, level, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(
                Component.translatable("item.blue_hearts.crying_charm.tooltip")
                        .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(
                Component.translatable("item.blue_hearts.crying_charm.effect_tooltip")
                        .withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltip.add(
                Component.translatable("item.blue_hearts.common_tooltip")
                        .withStyle(ChatFormatting.GRAY)
            );
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            // Rastrear tempo global do jogo
            long gameTime = player.level().getGameTime();

            // A cada 30 segundos (600 ticks)
            if (gameTime % 600 == 0) {
                CapabilitesHandler.getBlueBlood(player).ifPresent(current -> {
                    if (current.getMAXBlueBlood() <= 0) return;  // Sem "Max Blue Blood", sem efeito

                    if (current.isMaximum()) return;  // Já está no máximo

                    // Incrementar Blue Blood em 0.5
                    CapabilitesHandler.setAndSyncBlueBlood(player, bb -> {
                        bb.addBlueBlood(0.5F);
                    });
                });
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        return ICurioItem.super.canEquip(context, stack)
                && !CurioItemsHandler.hasCurio(context.entity(), InitItems.CRYING_CHARM.get());
    }
}
