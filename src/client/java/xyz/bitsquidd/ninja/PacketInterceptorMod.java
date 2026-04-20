package xyz.bitsquidd.ninja;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import xyz.bitsquidd.bits.config.FabricClientBitsConfig;
import xyz.bitsquidd.ninja.config.Config;
import xyz.bitsquidd.ninja.format.PacketLogger;

public final class PacketInterceptorMod implements ClientModInitializer {
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
        Config.load();
        new FabricClientBitsConfig(LoggerFactory.getLogger("PacketNinja")).startup();
    }

    public @NotNull PacketFilter getPacketFilter() {
        return packetFilter;
    }

    public @NotNull PacketLogger getPacketLogger() {
        return packetLogger;
    }

    public static void logPacket(Packet<?> packet) {
        if (logPackets && getInstance().getPacketFilter().shouldInterceptPacket(packet)) {
            getInstance().getPacketLogger().addPacket(packet);
        }
    }

}