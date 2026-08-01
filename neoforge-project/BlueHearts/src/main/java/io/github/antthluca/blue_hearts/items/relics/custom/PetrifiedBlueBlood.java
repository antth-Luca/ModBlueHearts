package io.github.antthluca.blue_hearts.items.relics.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class PetrifiedBlueBlood extends Item implements ICurioItem {
    public static final float ARMOR = 6.0F;
    public static final float TOUGHNESS_ARMOR = 2.0F;

    public PetrifiedBlueBlood() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.RARE));
    }
}
