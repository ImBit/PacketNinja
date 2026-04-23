package xyz.bitsquidd.ninja;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.bitsquidd.ninja.config.Config;
import xyz.bitsquidd.ninja.format.PacketLogger;
import xyz.bitsquidd.ninja.keybind.NinjaKeybind;

import java.util.Objects;

public final class PacketInterceptorMod implements ClientModInitializer {
    private static @Nullable PacketInterceptorMod instance;
    private final PacketFilter packetFilter = new PacketFilter();
    private final PacketLogger packetLogger = new PacketLogger();

    public static boolean logPackets = false;

    public PacketInterceptorMod() {
        if (instance != null) throw new IllegalStateException("PacketInterceptorMod instance already exists!");
        instance = this;
    }

    public static @NotNull PacketInterceptorMod getInstance() {
        return Objects.requireNonNull(instance, "PacketInterceptorMod instance is not initialized yet!");
    }

    @Override
    public void onInitializeClient() {
        Config.load();
        NinjaKeybind.register();
        ClientTickEvents.END_CLIENT_TICK.register(_ -> NinjaKeybind.tick());
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