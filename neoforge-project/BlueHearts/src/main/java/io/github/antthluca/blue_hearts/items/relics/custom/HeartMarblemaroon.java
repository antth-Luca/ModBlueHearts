package io.github.antthluca.blue_hearts.items.relics.custom;

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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class HeartMarblemaroon extends Item implements ICurioItem {
    public HeartMarblemaroon() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltips, flagIn);
        if (Screen.hasShiftDown()) {
            tooltips.add(
                    Component.translatable("item.blue_hearts.heart_marblemaroon.tooltip")
                            .withStyle(ChatFormatting.GRAY));
            tooltips.add(
                    Component.translatable("item.blue_hearts.heart_marblemaroon.effect_tooltip")
                            .withStyle(ChatFormatting.GRAY));
        } else {
            tooltips.add(
                    Component.translatable("item.blue_hearts.common_tooltip")
                            .withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            FoodData food = player.getFoodData();
            food.setExhaustion(0);
        }
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        return ICurioItem.super.canEquip(context, stack)
                && !CurioItemsHandler.hasCurio(context.entity(), InitItems.HEART_MARBLEMAROON.get());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        // Hunger
        if (slotContext.entity() instanceof Player player) {
            FoodData food = player.getFoodData();
            food.setFoodLevel(20);
            food.setSaturation(0);
        }

        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }
}
