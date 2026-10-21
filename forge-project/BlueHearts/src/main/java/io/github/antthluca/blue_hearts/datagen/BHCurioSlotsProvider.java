package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class BHCurioSlotsProvider extends CuriosDataProvider {
    public BHCurioSlotsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(BlueHearts.MODID, output, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries) {
        this.createEntities("charm_slot")
                .addEntities(EntityType.PLAYER)
                .addSlots("charm");

    }
}
