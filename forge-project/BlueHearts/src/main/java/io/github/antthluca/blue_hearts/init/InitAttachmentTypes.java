package io.github.antthluca.blue_hearts.init;

import com.mojang.serialization.Codec;
import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.serializers.custom.BlueBloodData;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class InitAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, BlueHearts.MODID);

    // Attachment Types
    public static final Supplier<AttachmentType<BlueBloodData>> PLAYER_BLUE_BLOOD = TYPES.register(
            "player_blue_blood", () -> AttachmentType.builder(BlueBloodData::getDefault)
                    .serialize(BlueBloodData.MAP_CODEC)
                    .copyOnDeath().build()
    );
}
