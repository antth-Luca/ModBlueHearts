package io.github.antthluca.blue_hearts.events;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.antthluca.blue_hearts.BlueHearts;
import io.github.antthluca.blue_hearts.capabilities.PlayerBlueBlood;
import io.github.antthluca.blue_hearts.handlers.CapabilitesHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BlueHearts.MODID)
public class BHCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("bhmanager")
                .requires(source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS))
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

        if (hearts < PlayerBlueBlood.MIN_BLUE_BLOOD) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_number"));
            return PlayerBlueBlood.MIN_BLUE_BLOOD;
        }

        if (hearts > PlayerBlueBlood.getGlobalMaxBlueBlood()) {
            source.sendSuccess(() -> Component.translatable("command.blue_hearts.capped_number"), true);
            hearts = PlayerBlueBlood.getGlobalMaxBlueBlood();
        }

        final int adjustedHearts = hearts;
        try {
            var targetPlayers = EntityArgument.getPlayers(ctx, "targets");

            targetPlayers.forEach(target -> {
                CapabilitesHandler.setAndSyncBlueBlood(target, bb -> {
                    bb.addMaxBlueBlood(adjustedHearts, target);
                    bb.addBlueBlood(adjustedHearts);
                });

                source.sendSuccess(
                        () -> Component.translatable(
                                "command.blue_hearts.success.add",
                                adjustedHearts,
                                target.getName().getString()
                        ), true
                );
            });

            return targetPlayers.size();
        } catch (Exception e) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_players"));
            return 0;
        }
    }

    private static int rmHearts(CommandContext<CommandSourceStack> ctx, int hearts) {
        CommandSourceStack source = ctx.getSource();

        if (hearts < PlayerBlueBlood.MIN_BLUE_BLOOD) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_number"));
            return PlayerBlueBlood.MIN_BLUE_BLOOD;
        }

        if (hearts > PlayerBlueBlood.getGlobalMaxBlueBlood()) {
            source.sendSuccess(() -> Component.translatable("command.blue_hearts.capped_number"), true);
            hearts = PlayerBlueBlood.getGlobalMaxBlueBlood();
        }
        final int adjustedHearts = hearts;

        try {
            var targetPlayers = EntityArgument.getPlayers(ctx, "targets");

            targetPlayers.forEach(target -> {
                CapabilitesHandler.setAndSyncBlueBlood(target, bb -> {
                    bb.subMaxBlueBlood(adjustedHearts);
                    bb.subBlueBlood(adjustedHearts);
                });

                source.sendSuccess(
                        () -> Component.translatable(
                                "command.blue_hearts.success.rm",
                                adjustedHearts,
                                target.getName().getString()
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

        if (hearts < PlayerBlueBlood.MIN_BLUE_BLOOD) {
            source.sendFailure(Component.translatable("command.blue_hearts.invalid_number"));
            return PlayerBlueBlood.MIN_BLUE_BLOOD;
        }

        if (hearts > PlayerBlueBlood.getGlobalMaxBlueBlood()) {
            source.sendSuccess(() -> Component.translatable("command.blue_hearts.capped_number"), true);
            hearts = PlayerBlueBlood.getGlobalMaxBlueBlood();
        }
        final int adjustedHearts = hearts;

        try {
            var targetPlayers = EntityArgument.getPlayers(ctx, "targets");

            targetPlayers.forEach(target -> {
                CapabilitesHandler.setAndSyncBlueBlood(target, bb -> {
                    bb.setMaxBlueBlood(adjustedHearts, target);
                    bb.setBlueBlood(adjustedHearts);
                });

                source.sendSuccess(
                        () -> Component.translatable(
                                "command.blue_hearts.success.set",
                                target.getName().getString(),
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
