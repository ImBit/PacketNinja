package xyz.bitsquidd.ninja;

import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class PacketFilter {
    private final Map<Class<? extends Packet<?>>, Boolean> packetFilters = new ConcurrentHashMap<>();

    public PacketFilter() {
        for (Class<? extends Packet<?>> packetClass : PacketRegistry.getAllPacketClasses()) {
            packetFilters.put(packetClass, false);
        }
    }

    public boolean shouldInterceptPacket(@NotNull Class<?> packetClass) {
        return packetFilters.getOrDefault(packetClass, false) && PacketRegistry.canHandle(packetClass);
    }

    public boolean shouldInterceptPacket(@NotNull Packet<?> packet) {
        return shouldInterceptPacket(packet.getClass());
    }

    public void togglePacketFilter(@NotNull String packetName) {
        PacketHandler<?> handler = PacketRegistry.findHandler(packetName);
        if (handler != null) {
            Class<? extends Packet<?>> packetClass = handler.getPacketClass();
            boolean currentState = packetFilters.getOrDefault(packetClass, false);
            packetFilters.put(packetClass, !currentState);
        }
    }

    public boolean isPacketEnabled(@NotNull Class<? extends Packet<?>> packetClass) {
        return packetFilters.getOrDefault(packetClass, false);
    }

    public Set<Class<? extends Packet<?>>> getKnownPackets() {
        return PacketRegistry.getAllPacketClasses();
    }

}