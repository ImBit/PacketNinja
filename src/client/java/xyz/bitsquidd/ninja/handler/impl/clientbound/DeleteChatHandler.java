package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundDebugSamplePacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class DeleteChatHandler extends PacketHandler<@NotNull ClientboundDeleteChatPacket> {

    public DeleteChatHandler() {
        super(
              ClientboundDeleteChatPacket.class,
              "DeleteChat",
              "Handles ClientboundDeleteChatPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundDeleteChatPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Signature"), Component.text(packet.messageSignature().toString()))
              )
        );
    }

}
