package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A bundle of information about a packet sent to a client. This structure is used for pretty formatting.
 */
@NullMarked
public final class PacketInfoBundle {
    private final PacketType type;

    private final Component name;
    private final List<PacketInfoSegment> segments;

    private PacketInfoBundle(PacketType type, Component name, List<PacketInfoSegment> segments) {
        this.type = type;
        this.name = name;
        this.segments = segments;
    }

    public static PacketInfoBundle of(PacketType type, Component name, List<PacketInfoSegment> segments) {
        return new PacketInfoBundle(type, name, segments);
    }

    public Component format() {
        Component titleComponent = Component.empty()
              .append(Component.text(type.icon + " "))
              .append(name
                    .color(type.primaryColor)
                    .decorate(TextDecoration.BOLD)
              );

        List<Component> segmentComponents = segments.stream().map(segment -> Component.empty()
              .color(type.secondaryColor)
              .append(Component.text("    ↪ "))
              .append(segment.getName())
              .append(Component.text(": "))
              .append(segment.getValue())
        ).collect(Collectors.toList());

        List<Component> allComponents = new ArrayList<>(List.of(titleComponent));
        allComponents.addAll(segmentComponents);

        return Component.join(JoinConfiguration.newlines(), allComponents);
    }

}
