package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;

import xyz.bitsquidd.ninja.handler.PacketType;

public class SinglePacketValue implements PacketInfo {
    private final Component value;

    public SinglePacketValue(Component value) {
        this.value = value;
    }

    @Override
    public Component format(PacketType type) {
        return value;
    }
}
