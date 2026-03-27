package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SetBorderCenterHandler extends PacketHandler<ClientboundSetBorderCenterPacket> {

    public SetBorderCenterHandler() {
        super(
              ClientboundSetBorderCenterPacket.class,
              "SetBorderCenter",
              "Handles world border center updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetBorderCenterPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfo.data(Component.text("X"), Component.text(packet.getNewCenterX())),
                    PacketInfo.data(Component.text("Z"), Component.text(packet.getNewCenterZ()))
              )
        );
    }

}
