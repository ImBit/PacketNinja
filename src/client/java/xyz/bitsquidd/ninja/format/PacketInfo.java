package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.Collection;
import java.util.List;

/**
 * Any information that a packet can give. Convenience methods for pretty formatting.
 */
public interface PacketInfo {
    Component format(PacketType type);


    static PacketInfo data(Component name, Component value) {
        return new SinglePacketData(name, value);
    }

    static PacketInfo value(Component value) {
        return new SinglePacketValue(value);
    }


    static PacketInfo list(Component name, PacketInfo... subInfo) {
        return list(name, List.of(subInfo));
    }

    static PacketInfo list(Component name, Collection<PacketInfo> subInfo) {
        return new ListPacketData(name, subInfo);
    }

}
