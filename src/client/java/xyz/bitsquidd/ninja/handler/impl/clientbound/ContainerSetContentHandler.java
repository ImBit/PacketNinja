package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class ContainerSetContentHandler extends PacketHandler<@NotNull ClientboundContainerSetContentPacket> {

    public ContainerSetContentHandler() {
        super(
              ClientboundContainerSetContentPacket.class,
              "ContainerSetContent",
              "Handles setting the contents of a container.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundContainerSetContentPacket packet) {
        List<String> itemsString = packet.items().stream()
              .map(FormatHelper::formatItemStack)
              .toList();

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ContainerID"), Component.text(packet.containerId())),
                    PacketInfoSegment.of(Component.text("StateID"), Component.text(packet.stateId())),
                    PacketInfoSegment.of(Component.text("Items"), Component.text(itemsString.toString())),
                    PacketInfoSegment.of(Component.text("CarriedItem"), Component.text(FormatHelper.formatItemStack(packet.carriedItem())))
              )
        );
    }
    
}