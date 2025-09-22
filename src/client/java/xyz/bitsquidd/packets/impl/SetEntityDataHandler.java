package xyz.bitsquidd.packets.impl;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import xyz.bitsquidd.packets.PacketHandler;

import java.util.List;

public class SetEntityDataHandler extends PacketHandler<ClientboundSetEntityDataPacket> {

    public SetEntityDataHandler() {
        super(ClientboundSetEntityDataPacket.class, "SetEntityData", "Handles entity Data Setting");
    }

    @Override
    protected MutableComponent formatPacketInternal(boolean outgoing, ClientboundSetEntityDataPacket packet) {
        StringBuilder builder = new StringBuilder();

        builder.append("SetEntityDataPacket\n");
        builder.append("  EntityId=").append(packet.id()).append("\n");

        List<SynchedEntityData.DataValue<?>> packedItems = packet.packedItems();
        if (packedItems==null || packedItems.isEmpty()) {
            builder.append("  No metadata updates\n");
        } else {
            builder.append("  Metadata:\n");
            for (SynchedEntityData.DataValue<?> dataValue : packedItems) {
                try {
                    int index = dataValue.id();
                    Object value = dataValue.value();
                    String type = value!=null ? value.getClass().getSimpleName():"null";
                    builder.append("    [Index ").append(index).append("] Type: ").append(type)
                            .append(" = ").append(value).append("\n");
                } catch (Exception e) {
                    builder.append("    [Error parsing DataValue: ").append(e.getMessage()).append("]\n");
                }
            }
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