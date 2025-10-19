package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

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
        String removedEntityIdList = formatList(packet.getEntityIds().stream().toList());

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Count"), Component.text(packet.getEntityIds().size())),
                    PacketInfoSegment.of(Component.text("EntityIds"), Component.text(removedEntityIdList))

              )
        );
    }

}