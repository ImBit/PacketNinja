package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

/**
 * Represents one or more display lines inside a packet info bundle.
 */
public interface PacketInfoRow {
    List<Component> format(PacketType type);
}
