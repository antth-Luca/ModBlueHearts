package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class InitCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB, BlueHearts.MODID);

    // Tabs
    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register(
        "main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.blue_hearts"))
            .icon(() -> new ItemStack(InitItems.VITAL_SAP.get()))
            .displayItems((dParams, out) -> {
                InitItems.ITEMS.getEntries().forEach(item -> out.accept(item.get()));
                InitFoods.FOODS.getEntries().forEach(item -> out.accept(item.get()));
            }).build()
    );
}
