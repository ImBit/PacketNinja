package xyz.bitsquidd.ninja.handler.impl.serverbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class CommandSuggestionsRequestHandler extends PacketHandler<@NotNull ServerboundCommandSuggestionPacket> {
    public CommandSuggestionsRequestHandler() {
        super(
              ServerboundCommandSuggestionPacket.class,
              "CommandSuggestionsRequest",
              "Handles command suggestion requests",
              PacketType.SERVERBOUND
        );
    }


    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ServerboundCommandSuggestionPacket packet) {
        var id = packet.getId();
        var text = packet.getCommand();

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Transaction ID"), Component.text(id)),
                    PacketInfoSegment.of(Component.text("Text"), Component.text(text))
              )
        );
    }
}
