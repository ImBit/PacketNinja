package xyz.bitsquidd.ninja;

import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.reflections.Reflections;

import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.lang.reflect.Modifier;
import java.util.*;

@NullMarked
public final class PacketRegistry {
    private static final Map<Class<? extends Packet<?>>, PacketHandler<?>> handlers = new HashMap<>();
    private static final Map<String, PacketHandler<?>> nameToHandler = new HashMap<>();

    static {
        registerHandlers();
    }

    private static void registerHandlers() {
        try {
            String basePackage = "xyz.bitsquidd.ninja.handler.impl";
            Reflections reflections = new Reflections(basePackage);

            Set<Class<? extends PacketHandler>> handlerClasses = reflections.getSubTypesOf(PacketHandler.class);
            for (Class<? extends PacketHandler> handlerClass : handlerClasses) {
                if (!Modifier.isAbstract(handlerClass.getModifiers())) {
                    PacketHandler<?> handler = handlerClass.getDeclaredConstructor().newInstance();
                    registerHandler(handler);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to register packet handlers via reflection", e);
        }
    }

    private static void registerHandler(PacketHandler<?> handler) {
        handlers.put(handler.getPacketClass(), handler);
        nameToHandler.put(handler.getFriendlyName().toLowerCase(), handler);
    }

    public static @Nullable PacketHandler<?> getHandlerForPacket(Packet<?> packet) {
        return handlers.get(packet.getClass());
    }


    public static boolean canHandle(Class<?> packetClass) {
        return handlers.containsKey(packetClass);
    }

    public static boolean canHandle(Packet<?> packet) {
        return canHandle(packet.getClass());
    }

    public static Collection<PacketHandler<?>> getAllHandlers() {
        List<PacketHandler<?>> allHandlers = new ArrayList<>(handlers.values());
        allHandlers.sort(Comparator.comparing(PacketHandler::getFriendlyName));
        return allHandlers;
    }

    public static Set<Class<? extends Packet<?>>> getAllPacketClasses() {
        return handlers.keySet();
    }

    public static @Nullable PacketHandler<?> findHandler(String input) {
        PacketHandler<?> handler = nameToHandler.get(input.toLowerCase());
        if (handler != null) return handler;

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