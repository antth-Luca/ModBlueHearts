package io.github.antthluca.blue_hearts.capabilities;

import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerBlueBloodProvider implements ICapabilitySerializable<CompoundTag> {
    private final LazyOptional<PlayerBlueBlood> optional = LazyOptional.of(this::getOrCreatePlayerBlueBlood);

    private PlayerBlueBlood data = null;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction dir) {
        if (cap == BHCapabilities.PLAYER_BLUE_BLOOD)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        getOrCreatePlayerBlueBlood().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreatePlayerBlueBlood().loadNBTData(nbt);
    }

    private PlayerBlueBlood getOrCreatePlayerBlueBlood() {
        if (this.data == null)
            this.data = CapabilitesHandler.createPlayerBlueBlood();

        return this.data;
    }
}
