package xyz.bitsquidd.ninja.packets.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import xyz.bitsquidd.ninja.packets.PacketHandler;

public class RemoveEntityPacketHandler extends PacketHandler<ClientboundRemoveEntitiesPacket> {

    public RemoveEntityPacketHandler() {
        super(ClientboundRemoveEntitiesPacket.class, "RemoveEntity", "Handles entity removal/despawning");
    }

    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ClientboundRemoveEntitiesPacket packet) {
        StringBuilder builder = new StringBuilder();
        builder.append("RemoveEntity\n  ");
        builder.append("Count=").append(packet.getEntityIds().size()).append("  ");

        if (packet.getEntityIds().size() <= 5) {
            builder.append("EntityIds=[");
            for (int i = 0; i < packet.getEntityIds().size(); i++) {
                builder.append(packet.getEntityIds().getInt(i));
                if (i < packet.getEntityIds().size() - 1) {
                    builder.append(",");
                }
            }
            builder.append("]");
        } else {
            builder.append("EntityIds=[");
            for (int i = 0; i < 3; i++) {
                builder.append(packet.getEntityIds().getInt(i));
                if (i < 2) {
                    builder.append(",");
                }
            }
            builder.append(",...+").append(packet.getEntityIds().size() - 3).append(" more]");
        }

        MutableComponent component = Component.literal(builder.toString());

        if (outgoing) {
            component.withColor(0x99ff99);
        } else {
            component.withColor(0xff9999);
        }

        return component;
    }
}