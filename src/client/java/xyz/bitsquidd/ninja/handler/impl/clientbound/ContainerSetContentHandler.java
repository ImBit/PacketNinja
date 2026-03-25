package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoList;
import xyz.bitsquidd.ninja.format.PacketInfoRow;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
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
        List<PacketInfoSegment> itemSegments = packet.items().stream()
              .limit(MAX_DISPLAYED_ENTRIES)
              .map(item -> PacketInfoSegment.of(Component.text("Item"), Component.text(FormatHelper.formatItemStack(item))))
              .toList();

        List<PacketInfoRow> rows = new ArrayList<>();
        rows.add(PacketInfoSegment.of(Component.text("ContainerID"), Component.text(packet.containerId())));
        rows.add(PacketInfoSegment.of(Component.text("StateID"), Component.text(packet.stateId())));

        if (!itemSegments.isEmpty()) {
            rows.add(PacketInfoList.of(Component.text("Items"), new ArrayList<>(itemSegments)));
        }

        int hiddenItems = packet.items().size() - itemSegments.size();
        if (hiddenItems > 0) {
            rows.add(PacketInfoSegment.of(Component.text("ItemsHidden"), Component.text(hiddenItems + " more")));
        }

        rows.add(PacketInfoSegment.of(Component.text("CarriedItem"), Component.text(FormatHelper.formatItemStack(packet.carriedItem()))));

        return PacketInfoBundle.ofRows(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }
    
}