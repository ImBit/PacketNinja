package xyz.bitsquidd.ninja;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.kyori.adventure.Adventure;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.PlayerLocales;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import xyz.bitsquidd.ninja.packets.PacketHandler;

import java.time.Duration;
import java.time.Instant;

public class PacketLogger {

    private Instant lastPacketTime = Instant.EPOCH;

    public void addPacket(Packet<?> packet) {
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

        Component component = handler.formatPacket(packet);
        sendChatMessage(component);
    }

    private void sendChatMessage(final Component component) {
        Audience client = MinecraftClientAudiences.of().audience();
        client.sendMessage(component);
    }

}