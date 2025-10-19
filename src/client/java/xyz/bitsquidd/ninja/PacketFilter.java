package xyz.bitsquidd.ninja;

import net.minecraft.network.protocol.Packet;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@NullMarked
public final class PacketFilter {
    private final Map<Class<? extends Packet<?>>, Boolean> packetFilters = new ConcurrentHashMap<>();

    public PacketFilter() {
        for (Class<? extends Packet<?>> packetClass : PacketRegistry.getAllPacketClasses()) {
            packetFilters.put(packetClass, false);
        }
    }

    public boolean shouldInterceptPacket(Class<?> packetClass) {
        return packetFilters.getOrDefault(packetClass, false) && PacketRegistry.canHandle(packetClass);
    }

    public boolean shouldInterceptPacket(Packet<?> packet) {
        return shouldInterceptPacket(packet.getClass());
    }

    public void togglePacketFilter(String packetName) {
        PacketHandler<?> handler = PacketRegistry.findHandler(packetName);
        if (handler != null) {
            Class<? extends Packet<?>> packetClass = handler.getPacketClass();
            boolean currentState = packetFilters.getOrDefault(packetClass, false);
            packetFilters.put(packetClass, !currentState);
        }
    }

    public boolean isPacketEnabled(Class<? extends Packet<?>> packetClass) {
        return packetFilters.getOrDefault(packetClass, false);
    }

    public Set<Class<? extends Packet<?>>> getKnownPackets() {
        return PacketRegistry.getAllPacketClasses();
    }

}