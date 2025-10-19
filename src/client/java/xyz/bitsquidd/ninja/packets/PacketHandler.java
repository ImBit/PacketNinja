package xyz.bitsquidd.ninja.packets;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;

public abstract class PacketHandler<T extends Packet<?>> {
    private final Class<T> packetClass;
    private final String friendlyName;
    private final String description;
    
    public PacketHandler(Class<T> packetClass, String friendlyName, String description) {
        this.packetClass = packetClass;
        this.friendlyName = friendlyName;
        this.description = description;
    }
    
    public Class<T> getPacketClass() {
        return packetClass;
    }
    
    public String getFriendlyName() {
        return friendlyName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean canHandle(Packet<?> packet) {
        return packetClass.isInstance(packet);
    }
    
    @SuppressWarnings("unchecked")
    public MutableComponent formatPacket(boolean outgoing, Packet<?> packet) {
        if (!canHandle(packet)) {
            throw new IllegalArgumentException("Cannot handle packet of type: " + packet.getClass().getSimpleName());
        }
        return formatPacketInternal(outgoing, (T) packet);
    }
    
    protected abstract MutableComponent formatPacketInternal(boolean outgoing, T packet);
}