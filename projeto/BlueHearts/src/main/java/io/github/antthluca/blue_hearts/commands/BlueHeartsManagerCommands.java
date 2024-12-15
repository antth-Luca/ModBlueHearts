package io.github.antthluca.blue_hearts.commands;

import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

public class BlueHeartsManagerCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("bhmanager")
            .then(Commands.literal("addhearts")
                .then(Commands.argument("int_hearts", IntegerArgumentType.integer(0))
                    .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> addHearts(ctx,
                            IntegerArgumentType.getInteger(ctx, "int_hearts"),
                            EntityArgument.getPlayer(ctx, "target")
                        ))
                    )
                )
            )
            .then(Commands.literal("rmhearts")
                .then(Commands.argument("int_hearts", IntegerArgumentType.integer(0))
                    .then(Commands.argument("target", EntityArgument.player())
                        .executes(ctx -> rmHearts(ctx,
                            IntegerArgumentType.getInteger(ctx, "int_hearts"),
                            EntityArgument.getPlayer(ctx, "target")
                        ))
                    )
                )
            )
        );
    }

    private static int addHearts(CommandContext<CommandSourceStack> ctx, int hearts, ServerPlayer targetPlayer) {
        CommandSourceStack source = ctx.getSource();

        if (hearts < 0) {
            source.sendFailure(new TextComponent("The number of hearts must be positive or zero."));
            return 0;
        }

        boolean success = targetPlayer.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).map(blueBlood -> {
            blueBlood.addMAXBlueBlood(hearts);
            blueBlood.addBlueBlood(hearts);
            return true;
        }).orElse(false);

        if (success) {
            source.sendSuccess(new TextComponent("Added " + hearts + " blue hearts to " + targetPlayer.getName().getString()), true);
            return 1;
        } else {
            source.sendFailure(new TextComponent("Target player does not support blue blood capability."));
            return 0;
        }
    }

    private static int rmHearts(CommandContext<CommandSourceStack> ctx, int hearts, ServerPlayer targetPlayer) {
        CommandSourceStack source = ctx.getSource();

        if (hearts < 0) {
            source.sendFailure(new TextComponent("The number of hearts must be positive or zero."));
            return 0;
        }

        boolean success = targetPlayer.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).map(blueBlood -> {
            blueBlood.subMAXBlueBlood(hearts);
            blueBlood.subBlueBlood(hearts);
            return true;
        }).orElse(false);

        if (success) {
            source.sendSuccess(new TextComponent("Removed " + hearts + " blue hearts from " + targetPlayer.getName().getString()), true);
            return 1;
        } else {
            source.sendFailure(new TextComponent("Target player does not support blue blood capability."));
            return 0;
        }
    }

    // "Set blue hearts of " + target.getName().getString() + " to " + hearts
}
