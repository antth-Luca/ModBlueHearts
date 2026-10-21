package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

public class BHItemTagsProvider extends ItemTagsProvider {
    public BHItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CuriosTags.CHARM)
                .add(InitItems.CRYING_CHARM.get())
                .add(InitItems.PETRIFIED_BLUE_BLOOD.get())
                .add(InitItems.HEART_MARBLEMAROON.get());
    }
}
