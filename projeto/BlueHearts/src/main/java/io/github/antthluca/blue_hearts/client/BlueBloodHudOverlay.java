package io.github.antthluca.blue_hearts.client;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.IIngameOverlay;

public class BlueBloodHudOverlay {
    private static final ResourceLocation FULL_BLUE_HEART = new ResourceLocation(BlueHearts.MODID, "textures/hud/full_blue_heart.png");
    private static final ResourceLocation MIDDLE_BLUE_HEART = new ResourceLocation(BlueHearts.MODID, "textures/hud/middle_blue_heart.png");

    public static final IIngameOverlay HUD_BLUE_BLOOD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2 - 91; // Centraliza os corações
        int y = height - 39;    // Define a altura
    
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    
        int blueBlood = ClientBlueBloodData.getPlayerBlueBlood();
        int maxBlueBlood = ClientBlueBloodData.getPlayerMaxBlueBlood();
    
        for (int i = 0; i < maxBlueBlood; i++) {
            if (blueBlood > i) {
                if (blueBlood - i > 0.5) {
                    // Renderiza o coração cheio
                    RenderSystem.setShaderTexture(0, FULL_BLUE_HEART);
                    GuiComponent.blit(poseStack, x + (i * 9), y - 54, 0, 0, 12, 12, 12, 12);
                } else {
                    // Renderiza o meio coração
                    RenderSystem.setShaderTexture(0, MIDDLE_BLUE_HEART);
                    GuiComponent.blit(poseStack, x + (i * 9), y - 54, 0, 0, 12, 12, 12, 12);
                }
            } else {
                break; // Sai do loop se não há mais blue blood a renderizar
            }
        }
    });    
}
