package io.github.antthluca.blue_hearts.init;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.blocks.custom.VitalBushBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BlueHearts.MODID);

    // Blocks
    public static final DeferredBlock<Block> VITAL_BUSH = BLOCKS.registerBlock(
            "vital_bush", (prop) -> new VitalBushBlock());
}
