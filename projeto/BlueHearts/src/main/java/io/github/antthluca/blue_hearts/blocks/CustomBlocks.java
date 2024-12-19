package io.github.antthluca.blue_hearts.blocks;

import io.github.antthluca.blue_hearts.init.InitFoods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBlocks {
    @SuppressWarnings("null")
    public static class VitalBushBlock extends SweetBerryBushBlock {
        public VitalBushBlock(Properties properties) {
            super(properties);
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            if (context.getClickedFace() == Direction.DOWN) {
                return this.defaultBlockState().setValue(AGE, 0); // Substitua AGE pelo seu atributo de estado, se houver.
            }
            return null; // Retorna nulo para impedir que seja colocado em superfícies inválidas.
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter block, BlockPos pos, CollisionContext context) {
            if (state.getValue(AGE) == 0) {
                return Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D);
            } else {
                return state.getValue(AGE) < 3 ? Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D) : super.getShape(state, block, pos, context);
            }
        }

        @Override
        public ItemStack getCloneItemStack(BlockGetter block, BlockPos pos, BlockState state) {
            return new ItemStack(InitFoods.VITAL_FRUIT.get());
        }

        @Override
        public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
            return world.getBlockState(pos.above()).isFaceSturdy(world, pos.above(), Direction.DOWN);
        }

        @Override
        public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource randomSource) {
            int i = state.getValue(AGE);
            if (i < 3 && world.getRawBrightness(pos.below(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, randomSource.nextInt(5) == 0)) {
                BlockState blockstate = state.setValue(AGE, Integer.valueOf(i + 1));
                world.setBlock(pos, blockstate, 2);
                world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
            }
        }

        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            return;  // Não causa dano!
        }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
            int i = state.getValue(AGE);
            boolean flag = i == 3;
            if (!flag && player.getItemInHand(interactionHand).is(Items.BONE_MEAL)) {
                return InteractionResult.PASS;
            } else if (i > 1) {
                popResource(level, pos, new ItemStack(InitFoods.VITAL_FRUIT.get(), (flag ? 1 : 0)));
                level.playSound(
                    (Player)null,
                    pos,
                    SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                    SoundSource.BLOCKS,
                    1.0F, 0.8F + level.random.nextFloat() * 0.4F
                );
                BlockState blockstate = state.setValue(AGE, Integer.valueOf(1));
                level.setBlock(pos, blockstate, 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState blockState) {
            if (level.random.nextInt() < 0.2f) {  // 20% de chance da bonemeal funcionar
                super.performBonemeal(level, randomSource, pos, blockState);
            }
        }
    }
}
