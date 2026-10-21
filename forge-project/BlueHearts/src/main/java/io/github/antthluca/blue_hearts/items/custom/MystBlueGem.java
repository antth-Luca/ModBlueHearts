package io.github.antthluca.blue_hearts.items.custom;

import io.github.antthluca.blue_hearts.init.InitEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class MystBlueGem extends Item {
    public MystBlueGem(Properties props) {
        super(props.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        if (Minecraft.getInstance().hasShiftDown()) {
            builder.accept(
                    Component.translatable("item.blue_hearts.myst_blue_gem.tooltip")
                            .withStyle(ChatFormatting.GRAY));
        } else {
            builder.accept(
                    Component.translatable("item.blue_hearts.common_tooltip")
                            .withStyle(ChatFormatting.GRAY));

        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            player.addEffect(new MobEffectInstance(InitEffects.CRYING_CHARM, -1, 0));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide()) {
            context.getPlayer().addEffect(new MobEffectInstance(InitEffects.CRYING_CHARM, -1, 0));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
