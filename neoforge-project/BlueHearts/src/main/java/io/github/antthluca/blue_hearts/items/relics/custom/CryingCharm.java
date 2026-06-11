package io.github.antthluca.blue_hearts.items.relics.custom;

import io.github.antthluca.blue_hearts.handlers.AttachmentsHandler;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.init.InitItems;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltips, flagIn);
        if (Screen.hasShiftDown()) {
            tooltips.add(
                    Component.translatable("item.blue_hearts.crying_charm.tooltip")
                            .withStyle(ChatFormatting.GRAY));
            tooltips.add(
                    Component.translatable("item.blue_hearts.crying_charm.effect_tooltip")
                            .withStyle(ChatFormatting.GRAY));
        } else {
            tooltips.add(
                    Component.translatable("item.blue_hearts.common_tooltip")
                            .withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayer) {
            // Rastrear tempo global do jogo
            long gameTime = serverPlayer.level().getGameTime();

            // A cada 30 segundos (600 ticks)
            if (gameTime % 600 == 0) {
                BlueBloodData currentData = serverPlayer.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);

                if (currentData.getMaxBlueBlood() <= 0) return; // Sem "Max Blue Blood", sem efeito

                if (currentData.isMaximum()) return;  // Já está no máximo

                // Incrementar Blue Blood em 0.5
                AttachmentsHandler.setAndSyncBlueBlood(
                        serverPlayer,
                        currentData.addBlueBlood(0.5F)
                );
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        return ICurioItem.super.canEquip(context, stack)
                && !CurioItemsHandler.hasCurio(context.entity(), InitItems.CRYING_CHARM.get());
    }
}
