package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SetBorderLerpSizeHandler extends PacketHandler<ClientboundSetBorderLerpSizePacket> {
    public SetBorderLerpSizeHandler() {
        super(
              ClientboundSetBorderLerpSizePacket.class,
              "SetBorderLerpSize",
              "Handles world border size lerp updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetBorderLerpSizePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfo.data(Component.text("Size"), Component.text(String.format("%s -> %s", packet.getOldSize(), packet.getNewSize()))),
                    PacketInfo.data(Component.text("Speed"), Component.text(packet.getLerpTime() + " ticks"))
              )
        );
    }
}
