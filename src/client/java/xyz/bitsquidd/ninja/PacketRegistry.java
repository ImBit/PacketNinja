package xyz.bitsquidd.ninja;

import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.bitsquidd.ninja.packets.PacketHandler;
import xyz.bitsquidd.ninja.packets.impl.*;

import java.util.*;

public class PacketRegistry {
    private static final @NotNull Map<Class<? extends Packet<?>>, PacketHandler<?>> handlers = new HashMap<>();
    private static final @NotNull Map<String, PacketHandler<?>> nameToHandler = new HashMap<>();

    static {
        registerHandlers();
    }

    private static void registerHandlers() {
        // Clientbound
        registerHandler(new RideEntityPacketHandler());
        registerHandler(new AddEntityPacketHandler());
        registerHandler(new AddCustomPayloadPacketHandler());
        registerHandler(new RemoveEntityPacketHandler());
        registerHandler(new SetEntityDataHandler());

        // Serverbound
        registerHandler(new InteractPacketHandler());
        registerHandler(new BlockPacketHandler());
        registerHandler(new SwingPacket());
    }

    private static void registerHandler(@NotNull PacketHandler<?> handler) {
        handlers.put(handler.getPacketClass(), handler);
        nameToHandler.put(handler.getFriendlyName().toLowerCase(), handler);
    }

    public static @Nullable PacketHandler<?> getHandlerForPacket(@NotNull Packet<?> packet) {
        return handlers.get(packet.getClass());
    }


    public static boolean canHandle(Class<?> packetClass) {
        return handlers.containsKey(packetClass);
    }

    public static boolean canHandle(@NotNull Packet<?> packet) {
        return canHandle(packet.getClass());
    }


    public static @NotNull Collection<PacketHandler<?>> getAllHandlers() {
        return handlers.values();
    }

    public static @NotNull Set<Class<? extends Packet<?>>> getAllPacketClasses() {
        return handlers.keySet();
    }

    public static @Nullable PacketHandler<?> findHandler(@NotNull String input) {
        PacketHandler<?> handler = nameToHandler.get(input.toLowerCase());
        if (handler!=null) return handler;

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