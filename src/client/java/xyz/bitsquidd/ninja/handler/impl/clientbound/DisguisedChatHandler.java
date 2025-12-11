package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class DisguisedChatHandler extends PacketHandler<@NotNull ClientboundDisguisedChatPacket> {

    public DisguisedChatHandler() {
        super(
              ClientboundDisguisedChatPacket.class,
              "DisguisedChat",
              "Handles ClientboundDisguisedChatPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundDisguisedChatPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ChatType"), Component.text(packet.chatType().chatType().getRegisteredName())),
                    PacketInfoSegment.of(Component.text("Message"), Component.text(packet.message().getString()))
              )
        );
    }

}
