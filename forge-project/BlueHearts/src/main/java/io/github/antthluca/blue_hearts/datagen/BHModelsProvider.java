package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitFoods;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BHModelsProvider extends ItemModelProvider {
    public BHModelsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, BlueHearts.MODID, helper);
    }

    @Override
    protected void registerModels() {
        basicItem(InitItems.VITAL_SAP.get());
        basicItem(InitItems.BLUE_EYE.get());
        basicItem(InitItems.MYST_BLUE_GEM.get());
        basicItem(InitItems.CRYING_CHARM.get());
        basicItem(InitItems.PETRIFIED_BLUE_BLOOD.get());
        basicItem(InitItems.HEART_MARBLEMAROON.get());
        basicItem(InitItems.BLUE_BLOOD_POTION.get());
        basicItem(InitItems.ORANGE_ANTIDOTE_POTION.get());
        basicItem(InitFoods.VITAL_FRUIT.get());
        basicItem(InitFoods.LAZULI_APPLE.get());
    }
}
