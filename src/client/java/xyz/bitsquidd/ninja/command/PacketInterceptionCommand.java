package xyz.bitsquidd.ninja.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import xyz.bitsquidd.ninja.PacketFilter;
import xyz.bitsquidd.ninja.PacketInterceptorMod;
import xyz.bitsquidd.ninja.PacketRegistry;
import xyz.bitsquidd.ninja.handler.PacketHandler;

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
        dispatcher.register(ClientCommandManager.literal("packetInterception")
                                                .then(ClientCommandManager.literal("start").executes(PacketInterceptionCommand::startLogging))
                                                .then(ClientCommandManager.literal("stop").executes(PacketInterceptionCommand::stopLogging))
                                                .then(ClientCommandManager.literal("filter").then(ClientCommandManager.literal("list").executes(PacketInterceptionCommand::listPackets)).then(ClientCommandManager.argument("packetName", StringArgumentType.word()).suggests(PACKET_SUGGESTIONS).executes(PacketInterceptionCommand::togglePacket))));
    }

    private static int startLogging(CommandContext<FabricClientCommandSource> context) {
        PacketInterceptorMod.logPackets = true;
        context.getSource().sendFeedback(Component.literal("✓ Started packet interception logging").withStyle(ChatFormatting.GREEN));
        return 1;
    }

    private static int stopLogging(CommandContext<FabricClientCommandSource> context) {
        PacketInterceptorMod.logPackets = false;
        context.getSource().sendFeedback(Component.literal("✗ Stopped packet interception logging").withStyle(ChatFormatting.RED));
        return 1;
    }

    private static int listPackets(CommandContext<FabricClientCommandSource> context) {
        PacketFilter filter = PacketInterceptorMod.getInstance().getPacketFilter();
        FabricClientCommandSource source = context.getSource();

        source.sendFeedback(Component.literal("=== Packet Interception Status ===").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD));

        source.sendFeedback(Component.literal("Logging: " + (PacketInterceptorMod.logPackets ? "ON" : "OFF")).withStyle(PacketInterceptorMod.logPackets ? ChatFormatting.GREEN : ChatFormatting.RED));

        source.sendFeedback(Component.literal("Available Packets:").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));

        for (PacketHandler<?> handler : PacketRegistry.getAllHandlers()) {
            boolean isEnabled = filter.isPacketEnabled(handler.getPacketClass());
            ChatFormatting color = isEnabled ? ChatFormatting.GREEN : ChatFormatting.RED;
            String symbol = isEnabled ? "✓" : "✗";

            source.sendFeedback(Component.literal("  " + symbol + " " + handler.getFriendlyName() + " - " + handler.getDescription()).withStyle(color));
        }

        source.sendFeedback(Component.literal("Commands:").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));
        source.sendFeedback(Component.literal("  /packetInterception start - Start logging").withStyle(ChatFormatting.YELLOW));
        source.sendFeedback(Component.literal("  /packetInterception stop - Stop logging").withStyle(ChatFormatting.YELLOW));
        source.sendFeedback(Component.literal("  /packetInterception filter <packet> - Toggle filter").withStyle(ChatFormatting.YELLOW));
        source.sendFeedback(Component.literal("Examples: RideEntity, AddEntity, RemoveEntity").withStyle(ChatFormatting.GRAY));

        return 1;
    }

    private static int togglePacket(CommandContext<FabricClientCommandSource> context) {
        String packetName = StringArgumentType.getString(context, "packetName");
        PacketFilter filter = PacketInterceptorMod.getInstance().getPacketFilter();
        FabricClientCommandSource source = context.getSource();

        PacketHandler<?> handler = PacketRegistry.findHandler(packetName);

        if (handler == null) {
            source.sendError(Component.literal("Unknown packet: " + packetName).withStyle(ChatFormatting.RED));
            source.sendFeedback(Component.literal("Available packets: " + String.join(", ", PacketRegistry.getAllHandlers().stream().map(PacketHandler::getFriendlyName).toList())).withStyle(ChatFormatting.YELLOW));
            return 0;
        }

        boolean wasEnabled = filter.isPacketEnabled(handler.getPacketClass());
        filter.togglePacketFilter(handler.getFriendlyName());
        boolean isEnabled = filter.isPacketEnabled(handler.getPacketClass());

        if (isEnabled) {
            source.sendFeedback(Component.literal("✓ Enabled interception for: " + handler.getFriendlyName() + " (" + handler.getPacketClass().getSimpleName() + ")").withStyle(ChatFormatting.GREEN));
        } else {
            source.sendFeedback(Component.literal("✗ Disabled interception for: " + handler.getFriendlyName() + " (" + handler.getPacketClass().getSimpleName() + ")").withStyle(ChatFormatting.RED));
        }

        return 1;
    }
}