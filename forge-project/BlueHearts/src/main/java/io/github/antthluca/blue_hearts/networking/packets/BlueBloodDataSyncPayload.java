package io.github.antthluca.blue_hearts.networking.packets;

import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record BlueBloodDataSyncPayload(BlueBloodData data) implements CustomPacketPayload {
    public static final Type<BlueBloodDataSyncPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(
            BlueHearts.MODID, "blue_blood_data_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BlueBloodDataSyncPayload> STREAM_CODEC =
        StreamCodec.composite(
                BlueBloodData.STREAM_CODEC,
                BlueBloodDataSyncPayload::data,
                BlueBloodDataSyncPayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
