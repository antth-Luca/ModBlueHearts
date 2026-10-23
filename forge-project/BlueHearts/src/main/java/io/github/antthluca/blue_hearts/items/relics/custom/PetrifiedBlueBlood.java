package io.github.antthluca.blue_hearts.items.relics.custom;

import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class PetrifiedBlueBlood extends Item implements ICurioItem {
    public static final float ARMOR = 6.0F;
    public static final float TOUGHNESS_ARMOR = 2.0F;

    public PetrifiedBlueBlood() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(itemStack, level, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(
                Component.translatable("item.blue_hearts.petrified_blue_blood.tooltip")
                        .withStyle(ChatFormatting.GRAY)
            );
            tooltip.add(
                Component.translatable("item.blue_hearts.petrified_blue_blood.effect_tooltip")
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
    public boolean canEquip(SlotContext context, ItemStack stack) {
        return ICurioItem.super.canEquip(context, stack)
                && !CurioItemsHandler.hasCurio(context.entity(), InitItems.PETRIFIED_BLUE_BLOOD.get());
    }
}
