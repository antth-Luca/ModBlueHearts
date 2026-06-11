package io.github.antthluca.blue_hearts.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitAttachmentTypes;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BlueBloodHUDOverlay {
    private static final ResourceLocation FULL_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/full_blue_heart.png");
    private static final ResourceLocation HALF_BLUE_HEART = ResourceLocation.fromNamespaceAndPath(BlueHearts.MODID,
            "textures/hud/half_blue_heart.png");

    public static void render(GuiGraphics gui, DeltaTracker partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player.isCreative()) return;

        BlueBloodData currentData = player.getData(InitAttachmentTypes.PLAYER_BLUE_BLOOD);
        if (!currentData.hasRemaining()) return; // Não desenha se não houver blue blood

        int x = minecraft.getWindow().getGuiScaledWidth() / 2 - 94;
        int y = minecraft.getWindow().getGuiScaledHeight() - 52;

        if ((int) player.getAbsorptionAmount() > 0) {
            y -= 10;
        }
        if ((int) player.getArmorValue() > 0) {
            y -= 10;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int fullHearts = (int) currentData.getBlueBlood(); // Total de corações inteiros
        boolean hasHalfHeart = currentData.getBlueBlood() > fullHearts; // Determina se há meio coração

        for (int i = 0; i < currentData.getMaxBlueBlood(); i++) {
            if (i < fullHearts) {
                // Renderiza corações inteiros
                gui.blit(FULL_BLUE_HEART, x + (i * 8), y, 0, 0, 16, 16, 16, 16);
            } else if (hasHalfHeart && i == fullHearts) {
                // Renderiza meio coração
                gui.blit(HALF_BLUE_HEART, x + (i * 8), y, 0, 0, 16, 16, 16, 16);
            } else {
                break; // Finaliza o loop após renderizar todos os corações necessários
            }
        }
    }
}
