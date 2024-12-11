package io.github.antthluca.blue_hearts.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerBlueBlood {
    private int blue_blood;
    private final int MIN_BLUE_BLOOD = 0;

    public int getBlueBlood() {
        return blue_blood;
    }

    public void addBlueBlood(int add) {
        this.blue_blood = blue_blood + add;
    }

    public void subBlueBlood(int sub) {
        this.blue_blood = Math.max(blue_blood - sub, MIN_BLUE_BLOOD);
    }

    public void copyFrom(PlayerBlueBlood source) {
        this.blue_blood = source.blue_blood;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("blue_blood", blue_blood);
    }

    public void loadNBTData(CompoundTag nbt) {
        blue_blood = nbt.getInt("blue_blood");
    }
}
