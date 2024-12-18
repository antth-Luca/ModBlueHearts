package io.github.antthluca.blue_hearts.client;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class BlueBloodHudOverlay {
    private static final ResourceLocation FULL_BLUE_HEART = new ResourceLocation(BlueHearts.MODID, "textures/hud/full_blue_heart.png");
    private static final ResourceLocation HALF_BLUE_HEART = new ResourceLocation(BlueHearts.MODID, "textures/hud/half_blue_heart.png");

    public static final IGuiOverlay HUD_BLUE_BLOOD = ((gui, poseStack, partialTick, width, height) -> {
        @SuppressWarnings("resource")
        Player player = Minecraft.getInstance().player;

        if (player.isCreative()) return;

        float blueBlood = ClientBlueBloodData.getPlayerBlueBlood();
        if (blueBlood <= 0) return; // Não desenha se não houver blue blood
        float maxBlueBlood = ClientBlueBloodData.getMaxBlueBlood();

        int x = width / 2 - 94;
        int y = height - 52;

        if ((int) player.getAbsorptionAmount() > 0) {
            y -= 10;
        }
        if ((int) player.getArmorValue() > 0) {
            y -= 10;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int fullHearts = (int) blueBlood; // Total de corações inteiros
        boolean hasHalfHeart = blueBlood > fullHearts; // Determina se há meio coração

        for (int i = 0; i < maxBlueBlood; i++) {
            if (i < fullHearts) {
                // Renderiza corações inteiros
                RenderSystem.setShaderTexture(0, FULL_BLUE_HEART);
                GuiComponent.blit(poseStack, x + (i * 8), y, 0, 0, 16, 16, 16, 16);
            } else if (hasHalfHeart && i == fullHearts) {
                // Renderiza meio coração
                RenderSystem.setShaderTexture(0, HALF_BLUE_HEART);
                GuiComponent.blit(poseStack, x + (i * 8), y, 0, 0, 16, 16, 16, 16);
            } else {
                break; // Finaliza o loop após renderizar todos os corações necessários
            }
        }
    });
}
