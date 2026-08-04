package io.github.antthluca.blue_hearts.tags;

import io.github.antthluca.blue_hearts.BlueHearts;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BHTags {
    public static final TagKey<Block> VALID_BLOCKS_FOR_VITAL_BUSH = BlockTags.create(
            Identifier.fromNamespaceAndPath(
                    BlueHearts.MODID,
                    "valid_blocks_for_vital_bush"));
}
