package xyz.bitsquidd.ninja;

import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.bits.log.Logger;
import xyz.bitsquidd.bits.util.Safety;
import xyz.bitsquidd.bits.util.reflection.ReflectionUtils;
import xyz.bitsquidd.bits.util.reflection.ScannerFlags;
import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.util.*;

@NullMarked
public final class PacketRegistry {
    private static final String HANDLER_PACKAGE = "xyz.bitsquidd.ninja.handler.impl";

    private static final Map<Class<? extends Packet<?>>, PacketHandler<?>> handlers = new HashMap<>();
    private static final Map<String, PacketHandler<?>> nameToHandler = new HashMap<>();

    static {
        try {
            ReflectionUtils.General.createClassesInDir(HANDLER_PACKAGE, PacketHandler.class, ScannerFlags.DEFAULT)
              .forEach(handler -> Safety.safeExecute(() -> registerHandler(handler)));
        } catch (Exception e) {
            Logger.error("Failed to register packet handlers");
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

    public static Optional<PacketHandler<?>> fromFriendlyName(String input) {
        PacketHandler<?> handler = nameToHandler.get(input.toLowerCase());
        if (handler != null) return Optional.of(handler);
        return Optional.empty();
    }

}