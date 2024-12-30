package io.github.antthluca.blue_hearts.networking.packet;

import io.github.antthluca.blue_hearts.client.ClientBlueBloodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class BlueBloodDataSyncS2CPacket {
    private final float blueBlood;
    private final float maxBlueBlood;

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

    public boolean handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            // Aqui está a lógica específica do pacote no cliente
            ClientBlueBloodData.setPlayerBlueBlood(blueBlood);
            ClientBlueBloodData.setMaxBlueBlood(maxBlueBlood);
        });
        context.setPacketHandled(true);
        return true;
    }    
}
