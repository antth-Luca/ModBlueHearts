package io.github.antthluca.blue_hearts.networking.packet;

import java.util.function.Supplier;

import io.github.antthluca.blue_hearts.client.ClientBlueBloodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class BlueBloodDataSyncS2CPacket {
    private final int blueBlood;
    private final int maxBlueBlood;

    public BlueBloodDataSyncS2CPacket(int blueBlood, int maxBlueBlood) {
        this.blueBlood = Math.max(0, blueBlood);
        this.maxBlueBlood = Math.max(0, maxBlueBlood);
    }

    public BlueBloodDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.blueBlood = buf.readInt();
        this.maxBlueBlood = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(blueBlood);
        buf.writeInt(maxBlueBlood);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Aqui está a lógica específica do pacote no cliente
            ClientBlueBloodData.setPlayerBlueBlood(blueBlood);
            ClientBlueBloodData.setMaxBlueBlood(maxBlueBlood);
        });
        context.setPacketHandled(true);
        return true;
    }    
}
