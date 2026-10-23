package io.github.antthluca.blue_hearts.blocks.custom;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.init.InitFoods;
import io.github.antthluca.blue_hearts.tags.BHTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VitalBushBlock extends SweetBerryBushBlock {
    public static final ResourceLocation VITAL_BUSH_HARVEST_LOOT_TABLE = ResourceLocation.fromNamespaceAndPath(
        BlueHearts.MODID, "block/vital_bush");

    public VitalBushBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return context.getClickedFace() == Direction.DOWN ? this.defaultBlockState().setValue(AGE, 0) : null;
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
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState aboveBlockState = level.getBlockState(pos.above());
        return aboveBlockState.is(BHTags.VALID_BLOCKS_FOR_VITAL_BUSH);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(AGE);
        if (i < 3
            && level.getRawBrightness(pos.below(), 0) >= 9
                && ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(5) == 0)) {
                    BlockState blockState = state.setValue(AGE, i + 1);
                    level.setBlock(pos, blockState, 2);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
                    ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    public void entityInside(BlockState p_57270_, Level p_57271_, BlockPos p_57272_, Entity p_57273_) {
        return;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            int i = state.getValue(AGE);
            boolean flag = i == 3;
            if (!flag && player.getItemInHand(player.getUsedItemHand()).is(Items.BONE_MEAL)) {
                return InteractionResult.PASS;
            } else if (i > 1) {
                ServerLevel serverLevel = (ServerLevel) level;
                LootParams lootParams = new LootParams.Builder(serverLevel)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                        .withParameter(LootContextParams.TOOL, player.getItemInHand(hand))
                        .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                        .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos))
                        .create(LootContextParamSets.BLOCK);
                LootTable lootTable = serverLevel.getServer().getLootData().getLootTable(VITAL_BUSH_HARVEST_LOOT_TABLE);

                List<ItemStack> drops = lootTable.getRandomItems(lootParams);
                for (ItemStack drop : drops) {
                    Block.popResource(serverLevel, pos, drop);
                }
                level.playSound(
                        null,
                        pos,
                        SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                        SoundSource.BLOCKS,
                        1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F
                );
                BlockState blockState = state.setValue(AGE, 1);
                level.setBlock(pos, blockState, 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter p_57256_, BlockPos p_57257_, BlockState p_57258_) {
        return new ItemStack(InitFoods.VITAL_FRUIT.get());
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (level.getRandom().nextInt() < 0.2F) {  // 20% of chance
            super.performBonemeal(level, random, pos, state);
        }
    }
}
