package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

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

        Entity entity = null;
        try {
            entity = Minecraft.getInstance().level.getEntity(entityId);
        } catch (Exception ignored) {}

        EntityType<?> entityType = entity != null ? entity.getType() : null;

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("EntityID"), Component.text(entityId)),
                    PacketInfoSegment.of(Component.text("Type*"), Component.text(entityType + "")), // Note: Type is not part of the packet, we resolve it for debugging purposes.
                    PacketInfoSegment.of(Component.text("MetadataCount"), Component.text(items.size()))
              )
        );
    }

}
