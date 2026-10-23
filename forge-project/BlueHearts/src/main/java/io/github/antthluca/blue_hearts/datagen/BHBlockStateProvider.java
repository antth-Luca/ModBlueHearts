package io.github.antthluca.blue_hearts.datagen;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.blocks.custom.VitalBushBlock;
import io.github.antthluca.blue_hearts.init.InitBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BHBlockStateProvider extends BlockStateProvider {
    public BHBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, BlueHearts.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        Block vitalBush = InitBlocks.VITAL_BUSH.get();
        getVariantBuilder(vitalBush).forAllStates(state -> {
            int age = state.getValue(VitalBushBlock.AGE);
            String name = "vital_bush_stage" + age;
            ModelFile model = models()
                    .cross(name, this.modLoc("block/" + name))
                    .renderType("cutout");
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }
}
