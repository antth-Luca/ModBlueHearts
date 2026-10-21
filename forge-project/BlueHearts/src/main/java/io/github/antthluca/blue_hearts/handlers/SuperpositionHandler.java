package io.github.antthluca.blue_hearts.handlers;

import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;

public class SuperpositionHandler {
    public static boolean cannotHunger(Player player) {
        boolean noHunger = false;

        if (player != null) {
            if (CurioItemsHandler.hasCurio(player, InitItems.HEART_MARBLEMAROON.get())) {
                noHunger = true;
            } else if (player.level().getDifficulty() == Difficulty.PEACEFUL) {
                noHunger = true;
            }
        }

        return noHunger;
    }
}
