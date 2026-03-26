package xyz.bitsquidd.ninja.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

import xyz.bitsquidd.ninja.PacketFilter;
import xyz.bitsquidd.ninja.PacketInterceptorMod;
import xyz.bitsquidd.ninja.PacketRegistry;
import xyz.bitsquidd.ninja.format.PacketLogger;
import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.util.List;

public class PacketInterceptionCommand {

    private static final SuggestionProvider<FabricClientCommandSource> PACKET_SUGGESTIONS = (context, builder) -> {
        for (String friendlyName : PacketRegistry.getAllHandlers().stream().map(PacketHandler::getFriendlyName).toList()) {
            if (friendlyName.toLowerCase().startsWith(builder.getRemaining().toLowerCase())) {
                builder.suggest(friendlyName);
            }
        }
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("packets")
              .then(ClientCommandManager.literal("start").executes(PacketInterceptionCommand::startLogging))
              .then(ClientCommandManager.literal("stop").executes(PacketInterceptionCommand::stopLogging))
              .then(ClientCommandManager.literal("filter")
                    .executes(PacketInterceptionCommand::listPackets)
                    .then(ClientCommandManager.argument("packetName", StringArgumentType.word())
                          .suggests(PACKET_SUGGESTIONS)
                          .executes(PacketInterceptionCommand::togglePacket))));
    }

    private static void sendMessage(Component message, ResponseType responseType) {
        PacketLogger.sendChatMessage(
              Component.empty()
                    .append(Component.text(responseType.icon + " "))
                    .append(message.color(responseType.color))
        );
    }

    public static void sendBlank() {
        PacketLogger.sendChatMessage(Component.empty());
    }

    private static int startLogging(CommandContext<FabricClientCommandSource> ctx) {
        PacketInterceptorMod.logPackets = true;

        sendBlank();
        sendMessage(Component.text("Started packet logging..."), ResponseType.SUCCESS);
        return Command.SINGLE_SUCCESS;
    }

    private static int stopLogging(CommandContext<FabricClientCommandSource> ctx) {
        PacketInterceptorMod.logPackets = false;

        sendBlank();
        sendMessage(Component.text("Stopped packet logging..."), ResponseType.ERROR);
        return Command.SINGLE_SUCCESS;
    }

    // TODO add clickable buttons!
    private static int listPackets(CommandContext<FabricClientCommandSource> ctx) {
        PacketFilter filter = PacketInterceptorMod.getInstance().getPacketFilter();

        sendBlank();
        sendMessage(
              Component.empty()
                    .append(Component.text("Logging: ").decorate(TextDecoration.BOLD))
                    .append(Component.text(PacketInterceptorMod.logPackets ? "ON" : "OFF")),
              ResponseType.INFO
        );

        List<PacketHandler<?>> enabledHandlers = PacketRegistry.getAllHandlers().stream()
              .filter(handler -> filter.isPacketEnabled(handler.getPacketClass()))
              .toList();
        List<PacketHandler<?>> disabledHandlers = PacketRegistry.getAllHandlers().stream()
              .filter(handler -> !filter.isPacketEnabled(handler.getPacketClass()))
              .toList();

        if (!enabledHandlers.isEmpty()) {
            sendBlank();
            sendMessage(
                  Component.text("Enabled: ").decorate(TextDecoration.BOLD, TextDecoration.UNDERLINED),
                  ResponseType.SUCCESS
            );
            for (PacketHandler<?> handler : enabledHandlers) {
                sendMessage(
                      Component.empty()
                            .append(Component.text(" " + handler.getFriendlyName() + ": ").decorate(TextDecoration.BOLD))
                            .append(Component.text(handler.getDescription())),
                      ResponseType.SUCCESS
                );
            }
        }

        if (!disabledHandlers.isEmpty()) {
            sendBlank();
            sendMessage(
                  Component.text("Disabled: ").decorate(TextDecoration.BOLD, TextDecoration.UNDERLINED),
                  ResponseType.ERROR
            );
            for (PacketHandler<?> handler : disabledHandlers) {
                sendMessage(
                      Component.empty()
                            .append(Component.text(" " + handler.getFriendlyName() + ": ").decorate(TextDecoration.BOLD))
                            .append(Component.text(handler.getDescription())),
                      ResponseType.ERROR
                );
            }
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int togglePacket(CommandContext<FabricClientCommandSource> ctx) {
        String packetName = StringArgumentType.getString(ctx, "packetName");
        PacketFilter filter = PacketInterceptorMod.getInstance().getPacketFilter();

        PacketHandler<?> handler = PacketRegistry.findHandler(packetName);

        if (handler == null) {
            sendMessage(
                  Component.empty()
                        .append(Component.text("Unknown packet: ").decorate(TextDecoration.BOLD))
                        .append(Component.text(packetName)),
                  ResponseType.ERROR
            );
            return Command.SINGLE_SUCCESS;
        }

        filter.togglePacketFilter(handler.getFriendlyName());
        boolean isEnabled = filter.isPacketEnabled(handler.getPacketClass());

        if (isEnabled) {
            sendMessage(
                  Component.empty()
                        .append(Component.text("Enabled interception for: ").decorate(TextDecoration.BOLD))
                        .append(Component.text(handler.getFriendlyName())),
                  ResponseType.SUCCESS
            );
        } else {
            sendMessage(
                  Component.empty()
                        .append(Component.text("Disabled interception for: ").decorate(TextDecoration.BOLD))
                        .append(Component.text(handler.getFriendlyName())),
                  ResponseType.ERROR
            );
        }

        return Command.SINGLE_SUCCESS;
    }

}