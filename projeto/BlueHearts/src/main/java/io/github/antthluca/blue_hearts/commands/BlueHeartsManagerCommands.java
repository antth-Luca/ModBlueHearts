package io.github.antthluca.blue_hearts.commands;

import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBloodProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class BlueHeartsManagerCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("bhmanager")
            .then(Commands.literal("addhearts")
                .then(Commands.argument("int_hearts", IntegerArgumentType.integer(0))
                    .then(Commands.argument("targets", EntityArgument.players())
                        .executes(ctx -> addHearts(ctx,
                            IntegerArgumentType.getInteger(ctx, "int_hearts")
                        ))
                    )
                )
            )
            .then(Commands.literal("rmhearts")
                .then(Commands.argument("int_hearts", IntegerArgumentType.integer(0))
                    .then(Commands.argument("targets", EntityArgument.players())
                        .executes(ctx -> rmHearts(ctx,
                            IntegerArgumentType.getInteger(ctx, "int_hearts")
                        ))
                    )
                )
            )
            .then(Commands.literal("sethearts")
                .then(Commands.argument("int_hearts", IntegerArgumentType.integer(0))
                    .then(Commands.argument("targets", EntityArgument.players())
                        .executes(ctx -> setHearts(ctx,
                            IntegerArgumentType.getInteger(ctx, "int_hearts")
                        ))
                    )
                )
            )
        );
    }

    private static int addHearts(CommandContext<CommandSourceStack> ctx, int hearts) {
        CommandSourceStack source = ctx.getSource();
        // Não pertmite números negativos
        if (hearts < 0) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_number"));
            return 0;
        }
        // Não permite número maior que 10
        if (hearts > 10) {
            source.sendSuccess(() -> Component.translatable("command.blue_hearts.capped_number"), true);
            hearts = 10;
        }
        final int adjustedHearts = hearts;

        try {
            var targetPlayers = EntityArgument.getPlayers(ctx, "targets");

            // Iterar sobre os jogadores e aplicar as mudanças
            targetPlayers.forEach(targetPlayer -> {
                targetPlayer.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blueBlood -> {
                    blueBlood.addMAXBlueBlood(adjustedHearts);
                    blueBlood.addBlueBlood(adjustedHearts);
                });
    
                source.sendSuccess(
                    () -> Component.translatable(
                        "command.blue_hearts.success.add",
                        adjustedHearts,
                        targetPlayer.getName().getString()
                    ), true);
            });

            return targetPlayers.size();
        } catch (Exception e) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_players"));
            return 0;
        }
    }

    private static int rmHearts(CommandContext<CommandSourceStack> ctx, int hearts) {
        CommandSourceStack source = ctx.getSource();
        // Não pertmite números negativos
        if (hearts < 0) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_number"));
            return 0;
        }
        // Não permite número maior que 10
        if (hearts > 10) {
            source.sendSuccess(() -> Component.translatable("command.blue_hearts.capped_number"), true);
            hearts = 10;
        }
        final int adjustedHearts = hearts;

        try {
            var targetPlayers = EntityArgument.getPlayers(ctx, "targets");

            // Iterar sobre os jogadores e aplicar as mudanças
            targetPlayers.forEach(targetPlayer -> {
                targetPlayer.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blueBlood -> {
                    blueBlood.subMAXBlueBlood(adjustedHearts);
                    blueBlood.subBlueBlood(adjustedHearts);
                });
    
                source.sendSuccess(
                    () -> Component.translatable(
                        "command.blue_hearts.success.rm",
                        adjustedHearts,
                        targetPlayer.getName().getString()
                    ), true);
            });

            return targetPlayers.size();
        } catch (Exception e) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_players"));
            return 0;
        }
    }

    private static int setHearts(CommandContext<CommandSourceStack> ctx, int hearts) {
        CommandSourceStack source = ctx.getSource();
        // Não pertmite números negativos
        if (hearts < 0) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_number"));
            return 0;
        }
        // Não permite número maior que 10
        if (hearts > 10) {
            source.sendSuccess(() -> Component.translatable("command.blue_hearts.capped_number"), true);
            hearts = 10;
        }
        final int adjustedHearts = hearts;

        try {
            var targetPlayers = EntityArgument.getPlayers(ctx, "targets");

            // Iterar sobre os jogadores e aplicar as mudanças
            targetPlayers.forEach(targetPlayer -> {
                targetPlayer.getCapability(PlayerBlueBloodProvider.PLAYER_BLUE_BLOOD).ifPresent(blueBlood -> {
                    blueBlood.setMAXBlueBlood(adjustedHearts);
                    blueBlood.setBlueBlood(adjustedHearts);
                });
    
                source.sendSuccess(
                    () -> Component.translatable(
                        "command.blue_hearts.success.set",
                        targetPlayer.getName().getString(),
                        adjustedHearts
                    ), true);
            });

            return targetPlayers.size();
        } catch (Exception e) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_players"));
            return 0;
        }
    }
}
