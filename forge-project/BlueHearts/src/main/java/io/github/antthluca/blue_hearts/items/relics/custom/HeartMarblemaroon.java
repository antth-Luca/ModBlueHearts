package io.github.antthluca.blue_hearts.items.relics.custom;

import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class HeartMarblemaroon extends Item implements ICurioItem {
    public static final int CONVERSION_HEARTS = 10;

    public HeartMarblemaroon() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(itemStack, level, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(
                Component.translatable("item.blue_hearts.heart_marblemaroon.tooltip")
                        .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(
                Component.translatable("item.blue_hearts.heart_marblemaroon.effect_tooltip")
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
            FoodData food = player.getFoodData();
            food.setFoodLevel(20);
            food.setSaturation(0);
        }
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        if (ICurioItem.super.canEquip(context, stack)
            && !(CurioItemsHandler.hasCurio(context.entity(), InitItems.HEART_MARBLEMAROON.get()))
            && context.entity() instanceof Player player) {
                CapabilitesHandler.getBlueBlood(player).ifPresent(bb -> {
                    return bb.getMAXBlueBlood() > 0;
                });
        }

        return false;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ICurioItem.super.onEquip(slotContext, prevStack, stack);

        if (slotContext.entity() instanceof Player player) {
            CapabilitesHandler.setAndSyncBlueBlood(player, bb -> {
                bb.addMaxBlueBlood(CONVERSION_HEARTS, player);
                bb.addBlueBlood(CONVERSION_HEARTS);
            });
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

        if (slotContext.entity() instanceof Player player) {
            CapabilitesHandler.setAndSyncBlueBlood(player, bb -> {
                bb.subMaxBlueBlood(CONVERSION_HEARTS);
                bb.subBlueBlood(CONVERSION_HEARTS);
            });
        }
    }
}
