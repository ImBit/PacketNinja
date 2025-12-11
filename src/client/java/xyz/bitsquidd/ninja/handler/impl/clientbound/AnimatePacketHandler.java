package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class AnimatePacketHandler  extends PacketHandler<@NotNull ClientboundAnimatePacket> {

    public AnimatePacketHandler() {
        super(
              ClientboundAnimatePacket.class,
              "Animate",
              "Handles ClientboundAnimatePacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundAnimatePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Action"), Component.text(packet.getAction()))
              )
        );
    }

}
