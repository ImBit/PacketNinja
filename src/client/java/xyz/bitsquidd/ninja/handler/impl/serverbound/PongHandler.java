package xyz.bitsquidd.ninja.handler.impl.serverbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.common.ServerboundPongPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class PongHandler extends PacketHandler<@NotNull ServerboundPongPacket> {

    public PongHandler() {
        super(
              ServerboundPongPacket.class,
              "Pong",
              "Handles when the player sends a pong packet.",
              PacketType.SERVERBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ServerboundPongPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ID"), Component.text(packet.getId()))
              )
        );
    }

}