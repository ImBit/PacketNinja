package xyz.bitsquidd.ninja.handler;

import net.minecraft.network.protocol.Packet;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;

import java.util.Collection;
import java.util.List;

@NullMarked
public abstract class PacketHandler<T extends Packet<?>> {
    private static final int MAX_DISPLAYED_ENTRIES = 5;


    protected final Class<T> packetClass;

    protected final String friendlyName;
    protected final String description;
    protected final PacketType packetType;

    public PacketHandler(Class<T> packetClass, String friendlyName, String description, PacketType packetType) {
        this.packetClass = packetClass;
        this.friendlyName = friendlyName;
        this.description = description;
        this.packetType = packetType;
    }

    public final Class<T> getPacketClass() {
        return packetClass;
    }

    public final String getFriendlyName() {
        return friendlyName;
    }

    public final String getDescription() {
        return description;
    }

    public final boolean canHandle(Packet<?> packet) {
        return packetClass.isInstance(packet);
    }

    @SuppressWarnings("unchecked")
    public final PacketInfoBundle getPacketInfo(Packet<?> packet) {
        if (!canHandle(packet)) {
            throw new IllegalArgumentException("Cannot handle packet of type: " + packet.getClass().getSimpleName());
        }

        return getPacketInfoInternal((T)packet);
    }

    protected abstract PacketInfoBundle getPacketInfoInternal(T packet);


    // Helper functions:
    protected final String formatList(Collection<?> entries) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        List<String> entriesList = entries.stream().map(Object::toString).toList();
        int size = entriesList.size();

        boolean tooMany = size > MAX_DISPLAYED_ENTRIES;

        for (int i = 0; i < MAX_DISPLAYED_ENTRIES; i++) {
            if (i >= size) break;
            sb.append(entriesList.get(i));
            if (i > 0) sb.append(",");
        }

        if (tooMany) {
            sb.append("...+").append(size - MAX_DISPLAYED_ENTRIES).append(" more]");
        } else {
            sb.append("]");
        }

        return sb.toString();
    }

}