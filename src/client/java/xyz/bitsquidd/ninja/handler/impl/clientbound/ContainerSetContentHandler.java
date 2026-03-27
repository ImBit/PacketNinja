package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
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
        var itemSegments = packet.items().stream()
              .limit(MAX_DISPLAYED_ENTRIES)
              .map(item -> PacketInfo.value(Component.text(FormatHelper.formatItemStack(item))))
              .toList();

        List<PacketInfo> rows = new ArrayList<>();
        rows.add(PacketInfo.data(Component.text("ContainerID"), Component.text(packet.containerId())));
        rows.add(PacketInfo.data(Component.text("StateID"), Component.text(packet.stateId())));

        if (!itemSegments.isEmpty()) {
            rows.add(PacketInfo.list(Component.text("Items"), new ArrayList<>(itemSegments)));
        }

        int hiddenItems = packet.items().size() - itemSegments.size();
        if (hiddenItems > 0) {
            rows.add(PacketInfo.data(Component.text("ItemsHidden"), Component.text(hiddenItems + " more")));
        }

        rows.add(PacketInfo.data(Component.text("CarriedItem"), Component.text(FormatHelper.formatItemStack(packet.carriedItem()))));

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }

}