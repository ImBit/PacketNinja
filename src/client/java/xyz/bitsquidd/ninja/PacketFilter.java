package xyz.bitsquidd.ninja;

import net.minecraft.network.protocol.Packet;
import xyz.bitsquidd.ninja.packets.PacketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PacketFilter {
    private final Map<Class<?>, Boolean> packetFilters = new ConcurrentHashMap<>();

    public PacketFilter() {
        for (Class<?> packetClass : PacketRegistry.getAllPacketClasses()) {
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
            Class<?> packetClass = handler.getPacketClass();
            boolean currentState = packetFilters.getOrDefault(packetClass, false);
            packetFilters.put(packetClass, !currentState);
        }
    }

    public boolean isPacketEnabled(String packetName) {
        PacketHandler<?> handler = PacketRegistry.findHandler(packetName);
        if (handler != null) {
            return packetFilters.getOrDefault(handler.getPacketClass(), false);
        }
        return false;
    }

    public boolean isPacketEnabled(Class<?> packetClass) {
        return packetFilters.getOrDefault(packetClass, false);
    }

    public Set<Class<?>> getKnownPackets() {
        return PacketRegistry.getAllPacketClasses();
    }

    public String getPacketFriendlyName(Class<?> packetClass) {
        PacketHandler<?> handler = PacketRegistry.getHandler(packetClass);
        return handler != null ? handler.getFriendlyName() : packetClass.getSimpleName();
    }
}