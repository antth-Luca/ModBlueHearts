package io.github.antthluca.blue_hearts.blocks;

import io.github.antthluca.blue_hearts.init.InitFoods;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class CustomBlocks {
    @SuppressWarnings("null")
    public static class VitalBushBlock extends SweetBerryBushBlock {
        public VitalBushBlock(Properties properties) {
            super(properties);
        }

        @Override
        public ItemStack getCloneItemStack(BlockGetter block, BlockPos pos, BlockState state) {
            return new ItemStack(InitFoods.VITAL_FRUIT.get());
        }

        @Override
        public void entityInside(BlockState p_57270_, Level p_57271_, BlockPos p_57272_, Entity p_57273_) {
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
