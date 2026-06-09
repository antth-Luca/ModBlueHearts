package io.github.antthluca.blue_hearts.items.relics.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class CryingCharm extends Item {
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
}
