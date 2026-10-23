package io.github.antthluca.blue_hearts.hud;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.client.ClientBlueBloodData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class BlueBloodHUDOverlay {
    private static final ResourceLocation FULL_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(
        BlueHearts.MODID, "textures/hud/full_blue_heart.png");
    private static final ResourceLocation HALF_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(
        BlueHearts.MODID, "textures/hud/half_blue_heart.png");
    private static final ResourceLocation EMPTY_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(
        BlueHearts.MODID, "textures/hud/empty_blue_heart.png");

    public static final IGuiOverlay HUD_BLUE_BLOOD = BlueBloodHUDOverlay::render;

    private static void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.gameMode == null) return;

        GameType gameMode = mc.gameMode.getPlayerMode();
        if (gameMode == GameType.CREATIVE || gameMode == GameType.SPECTATOR) return;

        Player player = mc.player;
        if (player == null) return;

        float blueBlood = ClientBlueBloodData.getBlueBlood();
        float maxBlueBlood = ClientBlueBloodData.getMaxBlueBlood();
        if (maxBlueBlood <= 0) return;

        int x = width / 2 - 91;
        int y = height - gui.leftHeight;

        int totalBlueHearts = Mth.ceil(maxBlueBlood);
        int blueHeartsRows = Math.max(1, Mth.ceil(totalBlueHearts / 10.0F));

        int fullBlueHearts = (int) blueBlood;
        boolean hasHalfHeart = blueBlood > fullBlueHearts;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        for (int c = 0; c < totalBlueHearts; c++) {
            ResourceLocation texture;
            if (c < fullBlueHearts) {
                texture = FULL_BLUE_HEART;
            } else if (hasHalfHeart && c == fullBlueHearts) {
                texture = HALF_BLUE_HEART;
            } else {
                texture = EMPTY_BLUE_HEART;
            }

            int heartX = x + (c % 10) * 8;
            int heartY = y - (c / 10) * 10;

            guiGraphics.blit(texture, heartX, heartY, 0, 0, 9, 9, 9, 9);
        }

        RenderSystem.disableBlend();

        gui.leftHeight += blueHeartsRows * 10;
    }
}