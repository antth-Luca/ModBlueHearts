package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.hud.BlueBloodHUDOverlay;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BHOverlays {
    @Mod.EventBusSubscriber(modid = BlueHearts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Registry {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerBelow(
                VanillaGuiOverlay.ARMOR_LEVEL.id(),
                "blue_blood",
                BlueBloodHUDOverlay.HUD_BLUE_BLOOD
            );
        }
    }

    @Mod.EventBusSubscriber(modid = BlueHearts.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class Render {
        @SubscribeEvent
        public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return;

            if (CurioItemsHandler.hasCurio(player, InitItems.HEART_MARBLEMAROON.get())
                    && (
                    event.getOverlay().id().equals(VanillaGuiOverlay.FOOD_LEVEL.id())
                            || event.getOverlay().id().equals(VanillaGuiOverlay.PLAYER_HEALTH.id())
            )) {
                event.setCanceled(true);
            }
        }
    }
}
