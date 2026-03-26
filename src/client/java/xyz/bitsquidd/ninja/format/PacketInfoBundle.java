package xyz.bitsquidd.ninja.format;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.NullMarked;

import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
import java.util.List;

/**
 * A bundle of information about a packet sent to a client. This structure is used for pretty formatting.
 */
@NullMarked
public final class PacketInfoBundle {
    private final PacketType type;

    private final Component name;
    private final List<PacketInfoRow> rows;

    private PacketInfoBundle(PacketType type, Component name, List<PacketInfoRow> rows) {
        this.type = type;
        this.name = name;
        this.rows = rows;
    }

    public static PacketInfoBundle of(PacketType type, Component name, List<? extends PacketInfoRow> rows) {
        return new PacketInfoBundle(type, name, new ArrayList<>(rows));
    }

    public Component format() {
        Component titleComponent = Component.empty()
              .append(Component.text(type.icon + " "))
              .append(name
                    .color(type.primaryColor)
                    .decorate(TextDecoration.BOLD)
              );

        List<Component> segmentComponents = new ArrayList<>();
        for (PacketInfoRow row : rows) {
            segmentComponents.addAll(row.format(type));
        }

        List<Component> allComponents = new ArrayList<>(List.of(titleComponent));
        allComponents.addAll(segmentComponents);

        return Component.join(JoinConfiguration.newlines(), allComponents);
    }

}
