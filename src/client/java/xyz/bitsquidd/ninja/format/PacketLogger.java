package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.network.protocol.Packet;
import org.jspecify.annotations.NullMarked;
import xyz.bitsquidd.ninja.PacketRegistry;
import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.time.Duration;
import java.time.Instant;

@NullMarked
public final class PacketLogger {

    private Instant lastPacketTime = Instant.EPOCH;

    public void addPacket(final Packet<?> packet) {
        PacketHandler<?> handler = PacketRegistry.getHandlerForPacket(packet);
        if (handler == null) return;

        // TODO add a config option for this.
        Instant currentTime = Instant.now();
        if (Duration.between(lastPacketTime, currentTime).compareTo(Duration.ofMillis(500)) < 0) {
            sendChatMessage(
                    Component.text("...", NamedTextColor.GRAY)
                            .hoverEvent(HoverEvent.showText(Component.text("Too many packets sent within 500ms, hiding."))
            ));
        }
        lastPacketTime = currentTime;

        PacketInfoBundle infoBundle = handler.getPacketInfo(packet);
        sendChatMessage(infoBundle.format());
    }

    private void sendChatMessage(final Component component) {
        Audience client = MinecraftClientAudiences.of().audience();
        client.sendMessage(component);
    }

}