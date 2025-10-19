package xyz.bitsquidd.ninja;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.bitsquidd.ninja.command.PacketInterceptionCommand;
import xyz.bitsquidd.ninja.format.PacketLogger;

public final class PacketInterceptorMod implements ClientModInitializer {
    public static final String MOD_ID = "packet-interceptor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static PacketInterceptorMod instance;
    private final PacketFilter packetFilter = new PacketFilter();
    private final PacketLogger packetLogger = new PacketLogger();

    public static boolean logPackets = false;

    public PacketInterceptorMod() {
        instance = this;
    }

    public static @NotNull PacketInterceptorMod getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            PacketInterceptionCommand.register(dispatcher);
        });
    }

    public @NotNull PacketFilter getPacketFilter() {
        return packetFilter;
    }

    public @NotNull PacketLogger getPacketLogger() {
        return packetLogger;
    }

    public static void logPacket(boolean outgoing, Packet<?> packet) {
        if (logPackets && getInstance().getPacketFilter().shouldInterceptPacket(packet)) {
            getInstance().getPacketLogger().addPacket(outgoing, packet);
        }
    }
}