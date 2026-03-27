package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

public class RemoveEntitiesHandler extends PacketHandler<@NotNull ClientboundRemoveEntitiesPacket> {
    public RemoveEntitiesHandler() {
        super(
              ClientboundRemoveEntitiesPacket.class,
              "RemoveEntities",
              "Handles entity removal/despawning",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundRemoveEntitiesPacket packet) {
        List<PacketInfo> rows = new ArrayList<>();

        var allEntityIds = packet.getEntityIds();
        int totalCount = allEntityIds.size();

        List<Integer> displayedIds = allEntityIds.intStream()
              .limit(MAX_DISPLAYED_ENTRIES)
              .boxed()
              .toList();

        rows.add(PacketInfo.data(Component.text("Count"), Component.text(totalCount)));

        if (!displayedIds.isEmpty()) {
            var idSegments = displayedIds.stream()
                  .map(id -> PacketInfo.value(Component.text(id)))
                  .toList();
            rows.add(PacketInfo.list(Component.text("EntityIds"), new ArrayList<>(idSegments)));
        }

        int hiddenEntities = totalCount - displayedIds.size();
        if (hiddenEntities > 0) {
            rows.add(PacketInfo.data(Component.text("EntityIdsHidden"), Component.text(hiddenEntities + " more")));
        }

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }

}