package xyz.bitsquidd.ninja.format;

import net.minecraft.network.protocol.Packet;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.PacketRegistry;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.ui.log.PacketLogEntry;
import xyz.bitsquidd.ninja.ui.log.PacketLogStore;

@NullMarked
public final class PacketLogger {
    public void addPacket(final Packet<?> packet) {
        PacketHandler<?> handler = PacketRegistry.getHandlerForPacket(packet);
        if (handler == null) return;

        PacketInfoBundle infoBundle = handler.getPacketInfo(packet);
        PacketLogStore.add(new PacketLogEntry(infoBundle.formatNative(), infoBundle.getType()));
    }
}