package io.github.antthluca.blue_hearts.integration.curios.items;

import java.util.List;

import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.integration.curios.init.InitItemsCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CryingCharm extends Item implements ICurioItem {
    public CryingCharm(Properties prop) {
        super(prop);
    }

    @SuppressWarnings("null")
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("item.blue_hearts.crying_charm.tooltip").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("item.blue_hearts.crying_charm.effect_tooltip").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("item.blue_hearts.common_tooltip").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            // Rastrear tempo global do jogo
            long gameTime = player.level().getGameTime();
            
            // A cada 30 segundos (600 ticks)
            if (gameTime % 600 == 0) {
                player.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blueBlood -> {
                    float maxBlueBlood = blueBlood.getMAXBlueBlood();
                    if (maxBlueBlood <= 0) return; // Sem "Max Blue Blood", sem efeito

                    float currentBlueBlood = blueBlood.getBlueBlood();
                    if (currentBlueBlood >= maxBlueBlood) return; // Já está no máximo

                    // Incrementar Blue Blood em 0.5
                    blueBlood.addBlueBlood(0.5f);
                });
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        return ICurioItem.super.canEquip(context, stack) && !CurioItemsHandler.hasCurio(context.entity(), InitItemsCurios.CRYING_CHARM.get());
    }
}