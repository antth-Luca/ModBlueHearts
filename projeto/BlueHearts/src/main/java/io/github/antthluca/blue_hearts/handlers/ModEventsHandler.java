package io.github.antthluca.blue_hearts.handlers;

import net.minecraftforge.fml.ModList;

public class ModEventsHandler {
    public static boolean isCuriosLoaded() {
        return ModList.get().isLoaded("curios");
    }
}
