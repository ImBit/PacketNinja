package xyz.bitsquidd;

import net.minecraft.network.protocol.Packet;
import xyz.bitsquidd.packets.PacketHandler;
import xyz.bitsquidd.packets.impl.*;

import java.util.*;

public class PacketRegistry {
    private static final Map<Class<?>, PacketHandler<?>> handlers = new HashMap<>();
    private static final Map<String, PacketHandler<?>> nameToHandler = new HashMap<>();

    static {
        registerHandlers();
    }

    private static void registerHandlers() {
        // Clientbound
        registerHandler(new RideEntityPacketHandler());
        registerHandler(new AddEntityPacketHandler());
        registerHandler(new RemoveEntityPacketHandler());

        // Serverbound
        registerHandler(new InteractPacketHandler());
        registerHandler(new BlockPacketHandler());
        registerHandler(new SwingPacket());
    }

    private static void registerHandler(PacketHandler<?> handler) {
        handlers.put(handler.getPacketClass(), handler);
        nameToHandler.put(handler.getFriendlyName().toLowerCase(), handler);
    }

    public static PacketHandler<?> getHandler(Class<?> packetClass) {
        return handlers.get(packetClass);
    }

    public static PacketHandler<?> getHandler(String friendlyName) {
        return nameToHandler.get(friendlyName.toLowerCase());
    }

    public static PacketHandler<?> getHandlerForPacket(Packet<?> packet) {
        return handlers.get(packet.getClass());
    }

    public static boolean canHandle(Class<?> packetClass) {
        return handlers.containsKey(packetClass);
    }

    public static boolean canHandle(Packet<?> packet) {
        return handlers.containsKey(packet.getClass());
    }

    public static Collection<PacketHandler<?>> getAllHandlers() {
        return handlers.values();
    }

    public static Set<Class<?>> getAllPacketClasses() {
        return handlers.keySet();
    }

    public static List<String> getAllFriendlyNames() {
        return handlers.values().stream().map(PacketHandler::getFriendlyName).sorted().toList();
    }

    public static PacketHandler<?> findHandler(String input) {
        PacketHandler<?> handler = nameToHandler.get(input.toLowerCase());
        if (handler != null) {
            return handler;
        }

        for (PacketHandler<?> h : handlers.values()) {
            if (h.getFriendlyName().toLowerCase().contains(input.toLowerCase())) {
                return h;
            }
        }

        for (PacketHandler<?> h : handlers.values()) {
            if (h.getPacketClass().getSimpleName().toLowerCase().contains(input.toLowerCase())) {
                return h;
            }
        }

        return null;
    }
}