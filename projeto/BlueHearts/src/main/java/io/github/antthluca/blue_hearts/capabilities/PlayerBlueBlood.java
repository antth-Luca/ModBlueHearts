package io.github.antthluca.blue_hearts.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerBlueBlood {
    private int blue_blood = 0;
    private final int MIN_BLUE_BLOOD = 0;
    private int MAX_BLUE_BLOOD = 0;

    // Blue Blood
    public int getBlueBlood() {
        return blue_blood;
    }

    public void addBlueBlood(int add) {
        if (add > 0) {
            this.blue_blood = Math.min(blue_blood + add, MAX_BLUE_BLOOD);
        }
    }

    public void subBlueBlood(int sub) {
        if (sub > 0) {
            this.blue_blood = Math.max(blue_blood - sub, MIN_BLUE_BLOOD);
        }
    }

    public void setBlueBlood(int set) {
        if (set >= 0) {
            this.blue_blood = set;
        }
    }

    // Max Blue Blood
    public int getMAXBlueBlood() {
        return MAX_BLUE_BLOOD;
    }

    public void addMAXBlueBlood(int add) {
        if (add > 0) {
            this.MAX_BLUE_BLOOD += add;
        }
    }

    public void subMAXBlueBlood(int sub) {
        if (sub > 0) {
            this.MAX_BLUE_BLOOD = Math.max(MAX_BLUE_BLOOD - sub, MIN_BLUE_BLOOD);
        }
    }

    public void setMAXBlueBlood(int set) {
        if (set >= 0) {
            this.MAX_BLUE_BLOOD = set;
        }
    }

    // Another
    public void copyFrom(PlayerBlueBlood source) {
        this.MAX_BLUE_BLOOD = source.MAX_BLUE_BLOOD;
        this.blue_blood = source.MAX_BLUE_BLOOD;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("blue_blood", blue_blood);
        nbt.putInt("max_blue_blood", MAX_BLUE_BLOOD);
    }

    public void loadNBTData(CompoundTag nbt) {
        blue_blood = nbt.getInt("blue_blood");
        MAX_BLUE_BLOOD = nbt.getInt("max_blue_blood");
    }
}
