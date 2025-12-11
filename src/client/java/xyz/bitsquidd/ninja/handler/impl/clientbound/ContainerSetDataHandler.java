package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class ContainerSetDataHandler extends PacketHandler<@NotNull ClientboundContainerSetDataPacket> {

    public ContainerSetDataHandler() {
        super(
              ClientboundContainerSetDataPacket.class,
              "ContainerSetData",
              "Handles ClientboundContainerSetDataPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundContainerSetDataPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ContainerID"), Component.text(packet.getContainerId())),
                    PacketInfoSegment.of(Component.text("Value"), Component.text(packet.getValue()))
              )
        );
    }
    
}