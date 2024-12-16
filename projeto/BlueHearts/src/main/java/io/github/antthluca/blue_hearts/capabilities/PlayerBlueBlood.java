package io.github.antthluca.blue_hearts.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerBlueBlood {
    private float blue_blood = 0;
    private final int MIN_BLUE_BLOOD = 0;
    private float MAX_BLUE_BLOOD = 0;
    private final int GLOBAL_MAX_BLUE_BLOOD = 10;

    // Blue Blood
    public float getBlueBlood() {
        return blue_blood;
    }

    public void addBlueBlood(float add) {
        if (add > 0) {
            this.blue_blood = Math.min(blue_blood + add, MAX_BLUE_BLOOD);
        }
    }

    public void subBlueBlood(float sub) {
        if (sub > 0) {
            this.blue_blood = Math.max(blue_blood - sub, MIN_BLUE_BLOOD);
        }
    }

    public void setBlueBlood(float set) {
        if (set >= 0) {
            this.blue_blood = Math.max(MIN_BLUE_BLOOD, Math.min(set, MAX_BLUE_BLOOD));
        }
    }

    // Max Blue Blood
    public float getMAXBlueBlood() {
        return MAX_BLUE_BLOOD;
    }

    public void addMAXBlueBlood(float add) {
        if (add > 0) {
            this.MAX_BLUE_BLOOD = Math.min(MAX_BLUE_BLOOD + add, GLOBAL_MAX_BLUE_BLOOD);
        }
    }

    public void subMAXBlueBlood(float sub) {
        if (sub > 0) {
            this.MAX_BLUE_BLOOD = Math.max(MAX_BLUE_BLOOD - sub, MIN_BLUE_BLOOD);
        }
    }

    public void setMAXBlueBlood(float set) {
        if (set >= 0) {
            this.MAX_BLUE_BLOOD = Math.max(MIN_BLUE_BLOOD, Math.min(set, GLOBAL_MAX_BLUE_BLOOD));
        }
    }

    // Another
    public void copyFrom(PlayerBlueBlood source) {
        this.MAX_BLUE_BLOOD = source.MAX_BLUE_BLOOD;
        this.blue_blood = source.MAX_BLUE_BLOOD;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("blue_blood", blue_blood);
        nbt.putFloat("max_blue_blood", MAX_BLUE_BLOOD);
    }

    public void loadNBTData(CompoundTag nbt) {
        blue_blood = nbt.getFloat("blue_blood");
        MAX_BLUE_BLOOD = nbt.getFloat("max_blue_blood");
    }
}
