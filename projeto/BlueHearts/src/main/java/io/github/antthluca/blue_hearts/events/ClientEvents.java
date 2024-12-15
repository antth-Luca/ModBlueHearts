package io.github.antthluca.blue_hearts.events;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.client.ClientBlueBloodData;
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
        // Verifica se estamos desenhando todos os elementos da HUD
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.player.isCreative()) return;

            int blueBlood = ClientBlueBloodData.getPlayerBlueBlood();
            if (blueBlood <= 0) return; // Não desenha se não houver blue blood

            // Configurações de posição e renderização
            int x = mc.getWindow().getGuiScaledWidth() / 2 - 94;  // ==> - 91 - 3
            int y = mc.getWindow().getGuiScaledHeight() - 52;
            // Calculando altura da linha contando a absorção
            if ((int) mc.player.getAbsorptionAmount() > 0) {
                y -= 10;
            }
            // Calculando altura da linha contando a armadura
            if ((int) mc.player.getArmorValue() > 0) {
                y -= 10;
            }
            // Renderizando
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, FULL_BLUE_HEART);

            // Renderiza os corações azuis
            for (int i = 0; i < blueBlood; i++) {
                GuiComponent.blit(event.getMatrixStack(), x + (i * 8), y, 0, 0, 16, 16, 16, 16);
            }
        }
    }
}
