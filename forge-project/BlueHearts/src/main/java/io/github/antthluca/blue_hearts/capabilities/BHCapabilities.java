package io.github.antthluca.blue_hearts.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class BHCapabilities {
    public static final Capability<PlayerBlueBlood> PLAYER_BLUE_BLOOD = CapabilityManager.get(
            new CapabilityToken<PlayerBlueBlood>() { });
}
