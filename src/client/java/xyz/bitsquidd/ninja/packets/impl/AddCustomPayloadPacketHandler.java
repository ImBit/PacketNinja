package xyz.bitsquidd.ninja.packets.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import xyz.bitsquidd.ninja.packets.PacketHandler;

public class AddCustomPayloadPacketHandler extends PacketHandler<ClientboundCustomPayloadPacket> {

    public AddCustomPayloadPacketHandler() {
        super(ClientboundCustomPayloadPacket.class, "CustomPayload", "Handles custom payload packets");
    }

    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ClientboundCustomPayloadPacket packet) {
        StringBuilder builder = new StringBuilder();
        builder.append("CustomPayload\n  ");

        CustomPacketPayload payload = packet.payload();
        // The payload type id, namespace, etc. are available from payload.type()
        CustomPacketPayload.Type<?> type = payload.type();

        builder.append("TypeId=").append(type.id().toString()).append("  ");
        builder.append("Namespace=").append(type.id().getNamespace()).append("  ");
        builder.append("Path=").append(type.id().getPath()).append("  ");
        builder.append("PayloadClass=").append(payload.getClass().getSimpleName()).append("  ");

        // Optionally, include more info depending on payload type (if you want to specialize)
        builder.append("PayloadString=").append(payload.toString());

        MutableComponent component = Component.literal(builder.toString());

        if (outgoing) {
            component.withColor(0x99ff99);
        } else {
            component.withColor(0xff9999);
        }

        return component;
    }
}