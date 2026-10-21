package io.github.antthluca.blue_hearts.items.potions.custom;

import io.github.antthluca.blue_hearts.init.InitEffects;
import io.github.antthluca.blue_hearts.items.potions.ItemBasePotion;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class OrangeAntidotePotion extends ItemBasePotion {
    public OrangeAntidotePotion(Properties props) {
        super(setDefaultProperties(props)
                .stacksTo(1)
                .rarity(Rarity.RARE));
    }

    @Override
    public void onConsumed(Level worldIn, Player player, ItemStack potion) {
        if (player instanceof ServerPlayer) {
            player.addEffect(new MobEffectInstance(InitEffects.ORANGE_ANTIDOTE, 1, 0));
        }
    }
}
