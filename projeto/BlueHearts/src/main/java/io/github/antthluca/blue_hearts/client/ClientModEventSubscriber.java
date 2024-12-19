package io.github.antthluca.blue_hearts.client;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
    @SuppressWarnings("removal")
    @SubscribeEvent
    public static void onRegisterRenderTypes(ModelEvent.RegisterAdditional event) {
        // Configurar RenderType para o VitalBush
        ItemBlockRenderTypes.setRenderLayer(InitBlocks.VITAL_BUSH.get(), RenderType.cutout());
    }
}
