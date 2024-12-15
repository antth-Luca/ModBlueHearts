package io.github.antthluca.blue_hearts;

import io.github.antthluca.blue_hearts.commands.BlueHeartsManagerCommands;
import io.github.antthluca.blue_hearts.init.InitEffects;
import io.github.antthluca.blue_hearts.init.InitItems;
import io.github.antthluca.blue_hearts.init.InitPotions;
import io.github.antthluca.blue_hearts.init.InitRecipes;
import io.github.antthluca.blue_hearts.networking.ModMessages;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BlueHearts.MODID)
public class BlueHearts {
    public static final String MODID = "blue_hearts";

    public BlueHearts() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        InitItems.ITEMS.register(bus);
        InitEffects.MOB_EFFECTS.register(bus);
        InitPotions.POTIONS.register(bus);

        bus.addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
            
            // Registrar receitas de poções
            InitRecipes.registerBrewingRecipes();

            // Registrar comandos no barramento de eventos do servidor
            MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        });
    }

    private void registerCommands(RegisterCommandsEvent event) {
        BlueHeartsManagerCommands.register(event.getDispatcher());
    }
}
