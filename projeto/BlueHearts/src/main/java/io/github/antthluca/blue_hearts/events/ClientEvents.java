package io.github.antthluca.blue_hearts.events;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID, value = Dist.CLIENT)
public class ClientEvents {
    private static final ResourceLocation FULL_BLUE_HEART = new ResourceLocation(BlueHearts.MODID, "textures/hud/full_blue_heart.png");

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        // Verifica se estamos no elemento correto da HUD
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            // Configurações de renderização
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, FULL_BLUE_HEART);

            // int blueBlood = ClientBlueHeartData.getPlayerBlueBlood(); // Obter valor sincronizado
            // int x = mc.getWindow().getGuiScaledWidth() / 2 - 91;
            // int y = mc.getWindow().getGuiScaledHeight() - 49;

            // for (int i = 0; i < blueBlood; i++) {
            //     GuiComponent.blit(event.getMatrixStack(), x + (i * 9), y, 0, 0, 12, 12, 12, 12);
            // }
        }
    }
}
