package xyz.bitsquidd.ninja.ui.log;

import net.minecraft.network.chat.Component;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.time.Instant;

public record PacketLogEntry(Component component, PacketType type, Instant timestamp) {
    public PacketLogEntry(Component component, PacketType type) {
        this(component, type, Instant.now());
    }
}
