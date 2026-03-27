package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SetBorderWarningDistanceHandler extends PacketHandler<ClientboundSetBorderWarningDistancePacket> {

    public SetBorderWarningDistanceHandler() {
        super(
              ClientboundSetBorderWarningDistancePacket.class,
              "SetBorderWarningDistance",
              "Handles world border warning distance updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetBorderWarningDistancePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Warning Distance"), Component.text(packet.getWarningBlocks() + " blocks"))
              )
        );
    }
}

