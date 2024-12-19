package io.github.antthluca.blue_hearts.blocks;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class CustomCropStageProvider extends BlockStateProvider {

    public CustomCropStageProvider(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, BlueHearts.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Registrar os estágios do cultivo
        registerCrop((CropBlock) InitBlocks.VITAL_BUSH.get(), "vital_bush_stage", CropBlock.AGE);
    }

    private void registerCrop(CropBlock cropBlock, String baseName, IntegerProperty ageProperty) {
        Function<BlockState, ConfiguredModel[]> stateMapper = state -> {
            int age = state.getValue(ageProperty); // Obtém o estágio da propriedade "idade"
            String modelName = baseName + age;

            ModelFile model = models().cross(
                    modelName,
                    new ResourceLocation(BlueHearts.MODID, "block/" + modelName)
            ).renderType("cutout");

            return new ConfiguredModel[]{new ConfiguredModel(model)};
        };

        getVariantBuilder(cropBlock).forAllStates(stateMapper);
    }
}
