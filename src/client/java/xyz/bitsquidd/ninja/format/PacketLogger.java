package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.PacketRegistry;
import xyz.bitsquidd.ninja.config.Config;
import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.time.Duration;
import java.time.Instant;

/**
 * Core logger for packets.
 */
@NullMarked
public final class PacketLogger {

    private Instant lastPacketTime = Instant.EPOCH;

    public void addPacket(final Packet<?> packet) {
        PacketHandler<?> handler = PacketRegistry.getHandlerForPacket(packet);
        if (handler == null) return;

        Duration delayRequired = Config.packetDelay;
        if (delayRequired.isPositive()) {
            Instant currentTime = Instant.now();
            if (Duration.between(lastPacketTime, currentTime).compareTo(delayRequired) < 0) {
                sendChatMessage(
                      Component.text("...", NamedTextColor.GRAY)
                            .hoverEvent(HoverEvent.showText(Component.text(String.format("Too many packets sent within %sms, hiding.", delayRequired))))
                );
                return;
            }
            lastPacketTime = currentTime;
        }

        PacketInfoBundle infoBundle = handler.getPacketInfo(packet);
        sendChatMessage(infoBundle.format());
    }

    public static void sendChatMessage(final Component component) {
        Minecraft.getInstance().execute(() ->
              MinecraftClientAudiences.of().audience().sendMessage(component)
        );
    }

}