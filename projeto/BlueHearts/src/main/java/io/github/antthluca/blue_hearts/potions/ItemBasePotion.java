package io.github.antthluca.blue_hearts.potions;

import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;

import io.github.antthluca.blue_hearts.BlueHearts;
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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// UNIFIED CLASS, of:
// 1. items/generic/ItemBasePotion
// 2. items/generic/ItemBase
// 3. api/items/ICreativeTabMember
// 4. handlers/SuperpositionHandler
// ALL CLASSES BY Aizistral FROM https://github.com/Aizistral-Studios/Enigmatic-Legacy/tree/1.19.X (Custom License)
// https://github.com/Aizistral-Studios/Enigmatic-Legacy/blob/1.19.X/LICENSE.md
public abstract class ItemBasePotion extends Item {
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

    @SuppressWarnings("null")
    @Override
    public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
        // Existential void
    }

    @Nullable
    public CreativeModeTab getCreativeTab() {
        return BlueHearts.TAB;
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
    public Component getName(@SuppressWarnings("null") ItemStack stack) {
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

    @SuppressWarnings("null")
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity living) {
        if (living instanceof Player player) {
            this.onConsumed(worldIn, player, stack);
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
    public int getUseDuration(@SuppressWarnings("null") ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(@SuppressWarnings("null") ItemStack stack) {
        return UseAnim.DRINK;
    }

    @SuppressWarnings("null")
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (this.canDrink(worldIn, playerIn, playerIn.getItemInHand(handIn))) {
            playerIn.startUsingItem(handIn);
            return super.use(worldIn, playerIn, handIn);
        } else {
            return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@SuppressWarnings("null") ItemStack stack) {
        return true;
    }

    public boolean canDrink(Level world, Player player, ItemStack potion) {
        return true;
    }

    public void onConsumed(Level worldIn, Player player, ItemStack potion) {
        // No operation
    }
}
