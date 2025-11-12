package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;
import java.util.stream.Collectors;

public class SetEntityDataHandler extends PacketHandler<@NotNull ClientboundSetEntityDataPacket> {

    public SetEntityDataHandler() {
        super(
              ClientboundSetEntityDataPacket.class,
              "SetEntityData",
              "Handles entity metadata updates",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetEntityDataPacket packet) {
        int entityId = packet.id();
        List<?> items = packet.packedItems();
        List<String> itemStrings = items.stream()
              .map(Object::toString)
              .collect(Collectors.toList());

        String formattedItems = FormatHelper.formatList(itemStrings, MAX_DISPLAYED_ENTRIES);

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("EntityID"), Component.text(entityId)),
                    PacketInfoSegment.of(Component.text("MetadataCount"), Component.text(items.size())),
                    PacketInfoSegment.of(Component.text("Metadata"), Component.text(formattedItems))
              )
        );
    }

}
