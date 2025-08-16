package xyz.bitsquidd.packets.impl;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import xyz.bitsquidd.packets.PacketHandler;

public class AddEntityPacketHandler extends PacketHandler<ClientboundAddEntityPacket> {

    public AddEntityPacketHandler() {
        super(ClientboundAddEntityPacket.class, "AddEntity", "Handles entity spawning");
    }

    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ClientboundAddEntityPacket packet) {
        StringBuilder builder = new StringBuilder();
        builder.append("AddEntity\n  ");
        builder.append("EntityId=").append(packet.getId()).append("  ");
        builder.append("UUID=").append(packet.getUUID().toString(), 0, 8).append("...  ");
        builder.append("Type=").append(BuiltInRegistries.ENTITY_TYPE.getKey(packet.getType())).append("  ");
        builder.append("Pos=[").append(String.format("%.2f", packet.getX())).append(",").append(String.format("%.2f", packet.getY())).append(",").append(String.format("%.2f", packet.getZ())).append("]  ");
        builder.append("Pitch=").append(String.format("%.1f", packet.getXRot())).append("  ");
        builder.append("Yaw=").append(String.format("%.1f", packet.getYRot())).append("  ");

        if (packet.getData() != 0) {
            builder.append("Data=").append(packet.getData()).append("  ");
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