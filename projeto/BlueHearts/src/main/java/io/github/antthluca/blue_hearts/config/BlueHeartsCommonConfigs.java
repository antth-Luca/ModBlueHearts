package io.github.antthluca.blue_hearts.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlueHeartsCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> INITIAL_BLUE_HEARTS_PER_PLAYER;

    static {
        BUILDER.push("Configs for Blue Hearts");

        INITIAL_BLUE_HEARTS_PER_PLAYER = BUILDER.comment("How many blue hearts should new players have when they enter the world for the first time?")
            .defineInRange("Initial Blue Hearts Per Player", 0, 0, 10);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
