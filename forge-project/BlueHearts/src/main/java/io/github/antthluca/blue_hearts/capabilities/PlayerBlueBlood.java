package io.github.antthluca.blue_hearts.capabilities;

import io.github.antthluca.blue_hearts.handlers.CurioItemsHandler;
import io.github.antthluca.blue_hearts.init.InitItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerBlueBlood {
    public static final int MIN_BLUE_BLOOD = 0;

    private final String NBT_CURRENT = "blue_blood";
    private final String NBT_CURRENT_MAX = "max_blue_blood";

    private float current = 0;
    private float current_max = 0;

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

    public void addBlueBlood(float add) {
        if (add > 0) {
            this.current = Math.min(this.current + add, current_max);
        }
    }

    public void subBlueBlood(float sub) {
        if (sub > 0) {
            this.current = Math.max(this.current - sub, MIN_BLUE_BLOOD);
        }
    }

    public void setBlueBlood(float set) {
        if (set >= 0) {
            this.current = Math.max(MIN_BLUE_BLOOD, Math.min(set, current_max));
        }
    }

    // Current Max Blue Blood
    public boolean isMaximum() {
        return this.current >= this.current_max;
    }

    public float getMaxBlueBlood() {
        return current_max;
    }

    public void addMaxBlueBlood(float add, Player player) {
        if (add > 0) {
            this.current_max = Math.min(this.current_max + add, getGlobalMaxBlueBlood(player));
        }
    }

    public void addMaxBlueBlood(float add) {
        addMaxBlueBlood(add, null);
    }

    public void subMaxBlueBlood(float sub) {
        if (sub > 0) {
            this.current_max = Math.max(this.current_max - sub, MIN_BLUE_BLOOD);
        }
    }

    public void setMaxBlueBlood(float set, Player player) {
        if (set >= 0) {
            this.current_max = Math.max(MIN_BLUE_BLOOD, Math.min(set, getGlobalMaxBlueBlood(player)));
        }
    }

    public void setMaxBlueBlood(float set) {
        setMaxBlueBlood(set, null);
    }

    // Another
    public void copyFrom(PlayerBlueBlood source) {
        this.current_max = source.current_max;
        this.current = source.current_max;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat(NBT_CURRENT, this.current);
        nbt.putFloat(NBT_CURRENT_MAX, this.current_max);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.current = nbt.getFloat(NBT_CURRENT);
        this.current_max = nbt.getFloat(NBT_CURRENT_MAX);
    }
}