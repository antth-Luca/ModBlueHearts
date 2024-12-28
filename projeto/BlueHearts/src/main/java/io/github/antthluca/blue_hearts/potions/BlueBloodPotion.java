package io.github.antthluca.blue_hearts.potions;

import io.github.antthluca.blue_hearts.init.InitEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class BlueBloodPotion extends ItemBasePotion {
    public BlueBloodPotion() {
        super(getDefaultProperties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public void onConsumed(Level worldIn, Player player, ItemStack potion) {
        if (player instanceof ServerPlayer) {
            player.addEffect(new MobEffectInstance(InitEffects.BLUE_BLOOD.get(), 1, 0));
        }
    }
}
