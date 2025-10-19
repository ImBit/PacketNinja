package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

/**
 * A segment of information about a packet, e.g. EntityID: 42
 */
@NullMarked
public final class PacketInfoSegment {
    private final Component name;
    private final Component value;

    private PacketInfoSegment(Component name, Component value) {
        this.name = name;
        this.value = value;
    }

    public static PacketInfoSegment of(Component name, Component value) {
        return new PacketInfoSegment(name, value);
    }

    public Component getName() {
        return name;
    }

    public Component getValue() {
        return value;
    }
}
