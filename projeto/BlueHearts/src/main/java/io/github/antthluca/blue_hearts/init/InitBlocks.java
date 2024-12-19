package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.blocks.CustomBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
        ForgeRegistries.BLOCKS, BlueHearts.MODID
    );

    // Blocks
    public static final RegistryObject<Block> VITAL_BUSH = BLOCKS.register(
        "vital_bush", () -> new CustomBlocks.VitalBushBlock(BlockBehaviour.Properties
            .copy(Blocks.SWEET_BERRY_BUSH)));
}
