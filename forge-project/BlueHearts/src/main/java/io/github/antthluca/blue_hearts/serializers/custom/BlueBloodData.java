package io.github.antthluca.blue_hearts.serializers.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.antthluca.blue_hearts.config.BHCommonConfig;
import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

public record BlueBloodData(float current, float current_max) {
    public static final int MIN_BLUE_BLOOD = 0;

    public static final MapCodec<BlueBloodData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
                Codec.FLOAT.fieldOf("current").forGetter(BlueBloodData::current),
                Codec.FLOAT.fieldOf("current_max").forGetter(BlueBloodData::current_max)
        ).apply(instance, BlueBloodData::new)
    );

    public static final StreamCodec<ByteBuf, BlueBloodData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, BlueBloodData::current,
            ByteBufCodecs.FLOAT, BlueBloodData::current_max,
            BlueBloodData::new
    );

    public static BlueBloodData getDefault() {
        int initialHearts = BHCommonConfig.INITIAL_BLUE_HEARTS_PER_PLAYER.get();

        return new BlueBloodData(initialHearts, initialHearts);
    }

    public static int getGlobalMaxBlueBlood() {
        return 10;
    }

    public static int getGlobalMaxBlueBlood(Player player) {
        if (player != null && CurioItemsHandler.hasCurio(player, InitItems.HEART_MARBLEMAROON.get())) {
            return 20;
        }

        return 10;
    }

    // GETTERS AND SETTERS
    // Current Blue Blood
    public boolean hasRemaining() {
        return current > 0;
    }

    public float getBlueBlood() {
        return current;
    }

    public BlueBloodData addBlueBlood(float add) {
        if (add > 0) {
            return new BlueBloodData(
                    Math.min(current + add, current_max),
                    current_max
            );
        }

        return this;
    }

    public BlueBloodData subBlueBlood(float sub) {
        if (sub > 0) {
            return new BlueBloodData(
                    Math.max(current - sub, MIN_BLUE_BLOOD),
                    current_max
            );
        }

        return this;
    }

    public BlueBloodData setBlueBlood(float set) {
        if (set >= 0) {
            return new BlueBloodData(
                    Math.max(
                            MIN_BLUE_BLOOD,
                            Math.min(set, current_max)
                    ),
                    current_max
            );
        }

        return this;
    }

    // Current Max Blue Blood
    public boolean isMaximum() {
        return current >= current_max;
    }

    public float getMaxBlueBlood() {
        return current_max;
    }

    public BlueBloodData addMaxBlueBlood(float add, Player player) {
        if (add > 0) {
            return new BlueBloodData(
                    current,
                    Math.min(current_max + add, getGlobalMaxBlueBlood(player))
            );
        }

        return this;
    }

    public BlueBloodData addMaxBlueBlood(float add) {
        return addMaxBlueBlood(add, null);
    }

    public BlueBloodData subMaxBlueBlood(float sub) {
        if (sub > 0) {
            return new BlueBloodData(
                    current,
                    Math.max(current_max - sub, MIN_BLUE_BLOOD)
            );
        }

        return this;
    }

    public BlueBloodData setMaxBlueBlood(float set, Player player) {
        if (set >= 0) {
            return new BlueBloodData(
                    current,
                    Math.max(
                            MIN_BLUE_BLOOD,
                            Math.min(set, getGlobalMaxBlueBlood(player))
                    )
            );
        }

        return this;
    }

    public BlueBloodData setMaxBlueBlood(float set) {
        return setMaxBlueBlood(set, null);
    }
}