package io.github.antthluca.blue_hearts.handlers;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ItemTooltipHandler {
    public static final Component HOLD_SHIFT = Component.translatable("item.blue_hearts.common.hold_shift").withStyle(ChatFormatting.DARK_PURPLE);
    public static final String LIST_ITEM = "– ";

    public static void addSpace(List<Component> tooltip) {
        tooltip.add(Component.empty());
    }

    public static void addLoreAndEffects(String itemName, int cont, List<Component> tooltip) {
        if (isShiftPressed()) {
            addAbilities(itemName, cont, tooltip);
        } else {
            addLore(itemName, tooltip);
            tooltip.add(HOLD_SHIFT);
        }
    }

    public static void addLore(String itemName, List<Component> tooltip) {
        addSpace(tooltip);
        tooltip.add(Component.translatable("item.blue_hearts." + itemName + ".lore").withStyle(ChatFormatting.GRAY));
    }

    public static void addAbilities(String itemName, int cont, List<Component> tooltip) {
        addSpace(tooltip);
        tooltip.add(Component.translatable("item.blue_hearts.common.abilities").append(":").withStyle(ChatFormatting.LIGHT_PURPLE));

        String baseKey = "item.blue_hearts." + itemName + ".ability";
        for (int c = 1; c <= cont; c++) {
            tooltip.add(
                    Component.literal(LIST_ITEM)
                            .append(Component.translatable(baseKey + c))
                            .withStyle(ChatFormatting.GRAY)
            );
        }
    }

    public static boolean isShiftPressed() {
        return Screen.hasShiftDown();
    }

}
