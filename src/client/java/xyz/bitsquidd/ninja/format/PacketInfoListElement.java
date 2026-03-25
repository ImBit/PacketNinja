package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;

import xyz.bitsquidd.ninja.handler.PacketType;

/**
 * Represents an element of a [PacketInfoList].
 */
public interface PacketInfoListElement {
    Component formatLine(PacketType type, String prefix);
}
