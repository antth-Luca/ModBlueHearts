package io.github.antthluca.blue_hearts.serializers.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.antthluca.blue_hearts.config.BHCommonConfig;

public record BlueBloodData(float current, float current_max) {
    public static final int MIN_BLUE_BLOOD = 0;
    public static final int GLOBAL_MAX_BLUE_BLOOD = 10;

    public static final Codec<BlueBloodData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.FLOAT.fieldOf("current").forGetter(BlueBloodData::current),
                Codec.FLOAT.fieldOf("current_max").forGetter(BlueBloodData::current_max)
        ).apply(instance, BlueBloodData::new)
    );

    public static BlueBloodData getDefault() {
        int initialHearts = BHCommonConfig.INITIAL_BLUE_HEARTS_PER_PLAYER.get();

        return new BlueBloodData(initialHearts, initialHearts);
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
        return current;
    }

    public BlueBloodData addMaxBlueBlood(float add) {
        if (add > 0) {
            return new BlueBloodData(
                    current,
                    Math.min(current_max + add, GLOBAL_MAX_BLUE_BLOOD)
            );
        }

        return this;
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

    public BlueBloodData setMaxBlueBlood(float set) {
        if (set >= 0) {
            return new BlueBloodData(
                    current,
                    Math.max(
                            MIN_BLUE_BLOOD,
                            Math.min(set, GLOBAL_MAX_BLUE_BLOOD)
                    )
            );
        }

        return this;
    }
}