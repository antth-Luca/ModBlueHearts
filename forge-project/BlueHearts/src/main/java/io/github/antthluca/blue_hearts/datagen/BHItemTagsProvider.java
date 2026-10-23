package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BHItemTagsProvider extends ItemTagsProvider {
    private static final TagKey<Item> CURIOS_CHARM = ItemTags.create(new ResourceLocation("curios", "charm"));

    public BHItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper helper) {
        super(output, lookupProvider, blockTags, BlueHearts.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CURIOS_CHARM)
                .add(InitItems.CRYING_CHARM.get())
                .add(InitItems.PETRIFIED_BLUE_BLOOD.get())
                .add(InitItems.HEART_MARBLEMAROON.get());
    }
}
