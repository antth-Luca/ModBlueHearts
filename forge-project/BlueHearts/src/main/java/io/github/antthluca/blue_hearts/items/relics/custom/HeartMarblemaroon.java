package io.github.antthluca.blue_hearts.items.relics.custom;

import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.handlers.ItemTooltipHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
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
        ItemTooltipHandler.addLoreAndEffects(InitItems.HEART_MARBLEMAROON.get().getDescriptionId(), 1, tooltip);
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
        if (ICurioItem.super.canEquip(context, stack)) return false;
        if (CurioItemsHandler.hasCurio(context.entity(), InitItems.HEART_MARBLEMAROON.get())) return false;
        if (!(context.entity() instanceof Player player)) return false;

        return CapabilitesHandler.getBlueBlood(player)
                .map(bb -> bb.getMaxBlueBlood() > 0)
                .orElse(false);
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
