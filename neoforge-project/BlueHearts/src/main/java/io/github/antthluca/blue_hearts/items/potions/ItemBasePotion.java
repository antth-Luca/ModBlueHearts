package io.github.antthluca.blue_hearts.items.potions;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

// UNIFIED CLASS, of:
// 1. items/generic/ItemBasePotion
// 2. items/generic/ItemBase
// 3. api/items/ICreativeTabMember
// 4. handlers/SuperpositionHandler
// ALL CLASSES BY Aizistral FROM https://github.com/Aizistral-Studios/Enigmatic-Legacy/tree/1.20.X (Custom License)
// https://github.com/Aizistral-Studios/Enigmatic-Legacy/blob/1.20.X/LICENSE.md
public class ItemBasePotion extends Item {
    protected static final Random random = new Random();
    protected boolean isPlaceholder;

    public ItemBasePotion() {
        this(getDefaultProperties());
    }

    public ItemBasePotion(Properties props) {
        super(props);
        this.isPlaceholder = false;
    }

    /* Métodos da funcionalidade base */

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        // Existencial void
    }

    public List<ItemStack> getCreativeTabStacks() {
        return ImmutableList.of(new ItemStack(this));
    }

    public static Properties getDefaultProperties() {
        Properties props = new Item.Properties();
        props.stacksTo(64);
        props.rarity(Rarity.COMMON);
        return props;
    }

    public static BlockHitResult rayTrace(Level worldIn, Player player, ClipContext.Fluid fluidMode) {
        return Item.getPlayerPOVHitResult(worldIn, player, fluidMode);
    }

    public Item setPlaceholder() {
        this.isPlaceholder = true;
        return this;
    }

    public boolean isPlaceholder() {
        return this.isPlaceholder;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component superName = super.getName(stack);
        if (this.isPlaceholder) {
            if (superName instanceof MutableComponent) {
                return ((MutableComponent) superName).withStyle(ChatFormatting.OBFUSCATED);
            }
        }
        return superName;
    }

    public static String minimizeNumber(double num) {
        int intg = (int)num;

        if (num - intg == 0)
            return "" + intg;
        else
            return "" + num;
    }

    /* Métodos de funcionalidade de poção */

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        if (living instanceof Player player) {
            this.onConsumed(level, player, stack);
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);

                if (stack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }

                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(@SuppressWarnings("null") ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (this.canDrink(level, player, player.getItemInHand(hand))) {
            player.startUsingItem(hand);
            return super.use(level, player, hand);
        } else {
            return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    public boolean canDrink(Level world, Player player, ItemStack potion) {
        return true;
    }

    public void onConsumed(Level worldIn, Player player, ItemStack potion) {
        // No operation
    }
}
