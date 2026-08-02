package io.github.antthluca.blue_hearts.events;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.handlers.SuperpositionHandler;
import io.github.antthluca.blue_hearts.hud.BlueBloodHUDOverlay;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = BlueHearts.MODID, value = Dist.CLIENT)
public class BHOverlays {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerBelow(
                VanillaGuiLayers.ARMOR_LEVEL,
                ResourceLocation.fromNamespaceAndPath(
                    BlueHearts.MODID,
                    "blue_blood_overlay"
                ),
                BlueBloodHUDOverlay::render
        );
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiLayerEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        if ((
            event.getName().equals(VanillaGuiLayers.FOOD_LEVEL)
            || event.getName().equals(VanillaGuiLayers.PLAYER_HEALTH)
        ) && CurioItemsHandler.hasCurio(player, InitItems.HEART_MARBLEMAROON.get())) {
                event.setCanceled(true);
        }
    }
}
