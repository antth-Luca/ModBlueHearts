package io.github.antthluca.blue_hearts.networking.packets;

import java.util.function.Supplier;

import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBlood;
import io.github.antthluca.blue_hearts.client.ClientBlueBloodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class BlueBloodDataSyncS2CPacket {
    private final float blueBlood;
    private final float maxBlueBlood;

    public BlueBloodDataSyncS2CPacket(PlayerBlueBlood data) {
        this(data.getBlueBlood(), data.getMaxBlueBlood());
    }

    public BlueBloodDataSyncS2CPacket(float blueBlood, float maxBlueBlood) {
        this.blueBlood = Math.max(0, blueBlood);
        this.maxBlueBlood = Math.max(0, maxBlueBlood);
    }

    public BlueBloodDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.blueBlood = buf.readFloat();
        this.maxBlueBlood = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(blueBlood);
        buf.writeFloat(maxBlueBlood);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientBlueBloodData.setBlueBlood(blueBlood);
            ClientBlueBloodData.setMaxBlueBlood(maxBlueBlood);
        });
        context.setPacketHandled(true);
        return true;
    }
}