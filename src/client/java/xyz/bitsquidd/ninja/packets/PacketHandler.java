package xyz.bitsquidd.ninja.packets;

import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class PacketHandler<T extends Packet<?>> {
    private final Class<T> packetClass;
    private final String friendlyName;
    private final String description;

    protected final PacketType packetType;
    
    public PacketHandler(Class<T> packetClass, String friendlyName, String description, PacketType packetType) {
        this.packetClass = packetClass;
        this.friendlyName = friendlyName;
        this.description = description;
        this.packetType = packetType;
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
    public Component formatPacket(Packet<?> packet) {
        if (!canHandle(packet)) throw new IllegalArgumentException("Cannot handle packet of type: " + packet.getClass().getSimpleName());

        return formatPacketInternal((T) packet);
    }
    
    protected abstract Component formatPacketInternal(T packet);
}