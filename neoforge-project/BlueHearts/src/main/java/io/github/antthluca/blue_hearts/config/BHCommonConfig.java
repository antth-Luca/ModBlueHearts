package io.github.antthluca.blue_hearts.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BHCommonConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Integer> INITIAL_BLUE_HEARTS_PER_PLAYER;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        BUILDER.push("Configs for Blue Hearts");

        INITIAL_BLUE_HEARTS_PER_PLAYER = BUILDER.comment("How many blue hearts should new players have when they enter the world for the first time?")
                .defineInRange("Initial Blue Hearts Per Player", 0, 0, 10);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
