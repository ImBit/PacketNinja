package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class AddEntityHandler extends PacketHandler<@NotNull ClientboundAddEntityPacket> {

    public AddEntityHandler() {
        super(
          ClientboundAddEntityPacket.class,
          "AddEntity",
          "Handles entity spawning",
          PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundAddEntityPacket packet) {
        return PacketInfoBundle.of(
          packetType,
          Component.text(friendlyName),
          List.of(
            PacketInfo.data(Component.text("EntityID"), Component.text(packet.getId())),
            PacketInfo.data(Component.text("Type"), Component.text(packet.getType() + "")),
            PacketInfo.data(Component.text("UUID"), Component.text(packet.getUUID() + "")),
            PacketInfo.data(Component.text("Pos"), Component.text(FormatHelper.formatPosition(packet.getX(), packet.getY(), packet.getZ()))),
            PacketInfo.data(Component.text("Rot"), Component.text(FormatHelper.formatRotation(packet.getXRot(), packet.getYRot())))
          )
        );
    }

}