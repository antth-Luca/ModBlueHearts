package io.github.antthluca.blue_hearts.integration.curios.items;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MystBlueGem extends Item {
    public MystBlueGem(Properties prop) {
        super(prop);
    }

    @SuppressWarnings("null")
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("item.blue_hearts.myst_blue_gem.tooltip").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("item.blue_hearts.common_tooltip").withStyle(ChatFormatting.GRAY));
        }
    }
}
