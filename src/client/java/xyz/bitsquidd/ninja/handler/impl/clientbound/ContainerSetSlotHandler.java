package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class ContainerSetSlotHandler extends PacketHandler<@NotNull ClientboundContainerSetSlotPacket> {

    public ContainerSetSlotHandler() {
        super(
              ClientboundContainerSetSlotPacket.class,
              "ContainerSetSlot",
              "Handles setting a slot in a container.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundContainerSetSlotPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ContainerID"), Component.text(packet.getContainerId())),
                    PacketInfoSegment.of(Component.text("StateID"), Component.text(packet.getStateId())),
                    PacketInfoSegment.of(Component.text("Slot"), Component.text(packet.getSlot())),
                    PacketInfoSegment.of(Component.text("Item"), Component.text(FormatHelper.formatItemStack(packet.getItem())))
              )
        );
    }

}