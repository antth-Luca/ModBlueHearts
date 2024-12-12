package io.github.antthluca.blue_hearts.networking.packet;

import java.util.function.Supplier;

import io.github.antthluca.blue_hearts.client.ClientBlueBloodData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class BlueBloodDataSyncS2CPacket {
    private final int blueBlood;

    public BlueBloodDataSyncS2CPacket(int blueBlood) {
        this.blueBlood = Math.max(0, blueBlood);
    }

    public BlueBloodDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.blueBlood = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(blueBlood);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Aqui está a lógica específica do pacote no cliente
            ClientBlueBloodData.set(blueBlood);
        });
        context.setPacketHandled(true);
        return true;
    }    
}
