package io.github.antthluca.blue_hearts.world.features;

import com.mojang.serialization.Codec;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


public class VitalBushFeature extends Feature<NoneFeatureConfiguration> {
    private final Holder<Block> bushBlock;

    public VitalBushFeature(Codec<NoneFeatureConfiguration> codec, Holder<Block> bushBlock) {
        super(codec);
        this.bushBlock = bushBlock;
    }

    @Override
    public boolean place(@SuppressWarnings("null") FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel world = ctx.level();
        BlockPos pos = ctx.origin();
        RandomSource random = ctx.random();

        // Define o número de tentativas para gerar arbustos por local
        int attempts = 16;

        for (int i = 0; i < attempts; i++) {
            BlockPos targetPos = pos.offset(
                random.nextInt(8) - random.nextInt(8),
                random.nextInt(4) - random.nextInt(4),
                random.nextInt(8) - random.nextInt(8)
            );

            BlockState bushState = bushBlock.value().defaultBlockState();

            // Verifica se o local é adequado (piso sólido e espaço vazio acima)
            if (world.isEmptyBlock(targetPos) &&
                world.getBlockState(targetPos.below()).is(BlockTags.create(new ResourceLocation(BlueHearts.MODID, "valid_blocks_for_vital_bush")))) {

                world.setBlock(targetPos, bushState, 2); // Define o bloco no mundo
            }
        }

        return true; // Retorna verdadeiro se pelo menos uma planta foi colocada
    }
}
