package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.integration.curios.init.InitItemsCurios;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB, BlueHearts.MODID
    );

    // Tabs
    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register(
        "main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.blue_hearts"))
            .icon(() -> new ItemStack(InitItems.VITAL_SAP.get()))
            .displayItems((displayParams, output) -> {
                // Default content without Curios
                InitItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                InitFoods.FOOD_ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                // Extra content with Curios
                if (BlueHearts.HAS_CURIOS) {
                    InitItemsCurios.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                }
            }).build()
    );
}
