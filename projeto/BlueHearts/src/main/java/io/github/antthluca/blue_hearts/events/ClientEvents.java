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
    private static final ResourceLocation HALF_BLUE_HEART = new ResourceLocation(BlueHearts.MODID, "textures/hud/half_blue_heart.png");

    @SuppressWarnings("null")
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.player.isCreative()) return;

            float blueBlood = ClientBlueBloodData.getPlayerBlueBlood();
            if (blueBlood <= 0) return; // Não desenha se não houver blue blood
            float maxBlueBlood = ClientBlueBloodData.getMaxBlueBlood();

            if (blueBlood <= 0 || maxBlueBlood <= 0) return;

            int x = mc.getWindow().getGuiScaledWidth() / 2 - 94;
            int y = mc.getWindow().getGuiScaledHeight() - 52;

            if ((int) mc.player.getAbsorptionAmount() > 0) {
                y -= 10;
            }
            if ((int) mc.player.getArmorValue() > 0) {
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
                    GuiComponent.blit(event.getMatrixStack(), x + (i * 8), y, 0, 0, 16, 16, 16, 16);
                } else if (hasHalfHeart && i == fullHearts) {
                    // Renderiza meio coração
                    RenderSystem.setShaderTexture(0, HALF_BLUE_HEART);
                    GuiComponent.blit(event.getMatrixStack(), x + (i * 8), y, 0, 0, 16, 16, 16, 16);
                } else {
                    break; // Finaliza o loop após renderizar todos os corações necessários
                }
            }
        }
    }
}
