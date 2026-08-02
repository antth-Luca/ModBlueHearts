package io.github.antthluca.blue_hearts.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.init.InitItems;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class BlueBloodHUDOverlay {
    private static final ResourceLocation FULL_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/full_blue_heart.png");
    private static final ResourceLocation HALF_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/half_blue_heart.png");
    private static final ResourceLocation EMPTY_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/empty_blue_heart.png");

    public static void render(GuiGraphics gui, DeltaTracker partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) return;

        GameType gameMode = mc.gameMode.getPlayerMode();
        if (gameMode == GameType.CREATIVE
          || gameMode == GameType.SPECTATOR) return;

        Player player = mc.player;
        if (player == null) return;

        BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);
        if (!currentData.hasRemaining()) return; // Não desenha se não houver blue blood

        Gui guiObj = mc.gui;
        int currentLeftHeight = guiObj.leftHeight;

        int x = mc.getWindow().getGuiScaledWidth() / 2 - 94;
        int y = mc.getWindow().getGuiScaledHeight() - currentLeftHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        float maxBlueBlood = currentData.getMaxBlueBlood();
        int totalBlueHearts = Mth.ceil(maxBlueBlood);

        int blueHeartsRows = Mth.ceil((float) totalBlueHearts / 10.0F);
        blueHeartsRows = Math.max(1, blueHeartsRows);

        int fullBlueHearts = (int) currentData.getBlueBlood();
        boolean hasHalfHeart = currentData.getBlueBlood() > fullBlueHearts;

        for (int c = 0; c < maxBlueBlood; c++) {
            int heartX = x + ((c % 10) * 8);
            int heartY = y - ((c / 10) * 10);

            if (c < fullBlueHearts) {
                // Renderiza corações inteiros
                gui.blit(FULL_BLUE_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);
            } else if (hasHalfHeart && c == fullBlueHearts) {
                // Renderiza meio coração
                gui.blit(HALF_BLUE_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);
            } else {
                // Renderiza coração vazio
                gui.blit(EMPTY_BLUE_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);
            }
        }

        guiObj.leftHeight += (blueHeartsRows * 10);
    }
}
