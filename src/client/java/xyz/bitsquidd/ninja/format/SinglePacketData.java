package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;

import xyz.bitsquidd.ninja.handler.PacketType;

/**
 * Represents a single bit of packet info.
 */
public class SinglePacketData implements PacketInfo {
    private final Component name;
    private final Component value;

    // TODO migrate to Object value + Formatters.format(value) rather than pre-formatting.
    SinglePacketData(Component name, Component value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Component format(PacketType type) {
        return Component.empty()
              .append(name.color(type.primaryColor))
              .append(Component.text(": "))
              .append(value.color(type.secondaryColor));
    }
}
