package xyz.bitsquidd.ninja.handler.impl.clientbound;

import com.mojang.brigadier.suggestion.Suggestion;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class CustomChatCompletionsHandler extends PacketHandler<@NotNull ClientboundCustomChatCompletionsPacket> {

    public CustomChatCompletionsHandler() {
        super(
              ClientboundCustomChatCompletionsPacket.class,
              "CustomChatCompletions",
              "Handles ClientboundCustomChatCompletionsPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundCustomChatCompletionsPacket packet) {
        String entriesString = FormatHelper.formatList(packet.entries(), MAX_DISPLAYED_ENTRIES);
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Entries"), Component.text(entriesString)),
                    PacketInfoSegment.of(Component.text("Duration"), Component.text(packet.action().toString()))
              )
        );
    }

}
