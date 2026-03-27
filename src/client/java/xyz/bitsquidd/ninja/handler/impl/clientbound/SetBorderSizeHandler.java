package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SetBorderSizeHandler extends PacketHandler<ClientboundSetBorderSizePacket> {

    public SetBorderSizeHandler() {
        super(
              ClientboundSetBorderSizePacket.class,
              "SetBorderSize",
              "Handles world border size updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetBorderSizePacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfo.data(Component.text("Size"), Component.text(packet.getSize()))
              )
        );
    }
}

