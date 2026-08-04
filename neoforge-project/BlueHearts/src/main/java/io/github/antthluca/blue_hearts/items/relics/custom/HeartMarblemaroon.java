package io.github.antthluca.blue_hearts.items.relics.custom;

import io.github.antthluca.blue_hearts.handlers.AttachmentsHandler;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.init.InitItems;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Consumer;

public class HeartMarblemaroon extends Item implements ICurioItem {
    public static final int CONVERSION_HEARTS = 10;

    public HeartMarblemaroon() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        if (Minecraft.getInstance().hasShiftDown()) {
            builder.accept(
                    Component.translatable("item.blue_hearts.heart_marblemaroon.tooltip")
                            .withStyle(ChatFormatting.GRAY));
            builder.accept(
                    Component.translatable("item.blue_hearts.heart_marblemaroon.effect_tooltip")
                            .withStyle(ChatFormatting.GRAY));
        } else {
            builder.accept(
                    Component.translatable("item.blue_hearts.common_tooltip")
                            .withStyle(ChatFormatting.GRAY));
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
                BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);
                return currentData.getMaxBlueBlood() > 0;
        }

        return false;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        ICurioItem.super.onEquip(slotContext, prevStack, stack);

        if (slotContext.entity() instanceof Player player) {
            AttachmentsHandler.setAndSyncBlueBlood(
                    player,
                    player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD)
                            .addMaxBlueBlood(CONVERSION_HEARTS, player)
                            .addBlueBlood(CONVERSION_HEARTS)
            );
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

        if (slotContext.entity() instanceof Player player) {
            AttachmentsHandler.setAndSyncBlueBlood(
                    player,
                    player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD)
                            .subMaxBlueBlood(CONVERSION_HEARTS)
                            .subBlueBlood(CONVERSION_HEARTS)
            );
        }
    }
}
