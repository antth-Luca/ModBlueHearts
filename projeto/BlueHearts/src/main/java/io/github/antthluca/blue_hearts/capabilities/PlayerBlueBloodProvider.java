package io.github.antthluca.blue_hearts.capabilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerBlueBloodProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerBlueBlood> PLAYER_BLUE_BLOOD = CapabilityManager.get(new CapabilityToken<PlayerBlueBlood>() { });

    private PlayerBlueBlood blue_blood = null;
    private final LazyOptional<PlayerBlueBlood> optional = LazyOptional.of(this::createPlayerBlueBlood);

    private PlayerBlueBlood createPlayerBlueBlood() {
        if (this.blue_blood == null) {
            this.blue_blood = new PlayerBlueBlood();
        }

        return this.blue_blood;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @NotNull Direction side) {
        if (cap == PLAYER_BLUE_BLOOD) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerBlueBlood().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerBlueBlood().loadNBTData(nbt);
    }

    public class Storage {
    }
}
