package io.github.antthluca.blue_hearts.hud;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class BlueBloodHUDOverlay {
    private static final Identifier FULL_BLUE_HEART = Identifier.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/full_blue_heart.png");
    private static final Identifier HALF_BLUE_HEART = Identifier.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/half_blue_heart.png");
    private static final Identifier EMPTY_BLUE_HEART = Identifier.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/empty_blue_heart.png");

    public static void render(GuiGraphicsExtractor gui, DeltaTracker partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;

        GameType gameMode = mc.gameMode.getPlayerMode();
        if (gameMode == GameType.CREATIVE
          || gameMode == GameType.SPECTATOR) return;

        Player player = mc.player;
        if (player == null) return;

        BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);

        Gui guiObj = mc.gui;
        int currentLeftHeight = guiObj.leftHeight;

        int x = mc.getWindow().getGuiScaledWidth() / 2 - 91;
        int y = mc.getWindow().getGuiScaledHeight() - currentLeftHeight;

        float maxBlueBlood = currentData.getMaxBlueBlood();
        int totalBlueHearts = Mth.ceil(maxBlueBlood);

        int blueHeartsRows = Mth.ceil((float) totalBlueHearts / 10.0F);
        blueHeartsRows = Math.max(1, blueHeartsRows);

        int fullBlueHearts = (int) currentData.getBlueBlood();
        boolean hasHalfHeart = currentData.getBlueBlood() > fullBlueHearts;

        for (int c = 0; c < maxBlueBlood; c++) {
            Identifier texture_loc;
            if (c < fullBlueHearts) {
                // Renderiza corações inteiros
                texture_loc = FULL_BLUE_HEART;
            } else if (hasHalfHeart && c == fullBlueHearts) {
                // Renderiza meio coração
                texture_loc = HALF_BLUE_HEART;
            } else {
                // Renderiza coração vazio
                texture_loc = EMPTY_BLUE_HEART;
            }

            int heartX = x + ((c % 10) * 8);
            int heartY = y - ((c / 10) * 10);

            gui.blit(RenderPipelines.GUI_TEXTURED, texture_loc, heartX, heartY, 0, 0, 9, 9, 9, 9);
        }

        guiObj.leftHeight += (blueHeartsRows * 10);
    }
}
