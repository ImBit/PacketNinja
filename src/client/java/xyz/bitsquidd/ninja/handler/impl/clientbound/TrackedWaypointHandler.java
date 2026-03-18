package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundTrackedWaypointPacket;
import net.minecraft.world.waypoints.TrackedWaypoint;
import net.minecraft.world.waypoints.Waypoint;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class TrackedWaypointHandler extends PacketHandler<@NotNull ClientboundTrackedWaypointPacket> {

    public TrackedWaypointHandler() {
        super(
              ClientboundTrackedWaypointPacket.class,
              "TrackedWaypoint",
              "Handles waypoint packets",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ClientboundTrackedWaypointPacket packet) {
        TrackedWaypoint waypoint = packet.waypoint();
        String id = waypoint.id().map(Object::toString, s -> s);
        Waypoint.Icon icon = waypoint.icon();

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Operation"), Component.text(packet.operation() + "")),
                    PacketInfoSegment.of(Component.text("ID"), Component.text(id)),
                    PacketInfoSegment.of(Component.text("Icon"), Component.text(icon.style.identifier().toString())),
                    PacketInfoSegment.of(Component.text("Location"), Component.text(extractLocation(waypoint)))
              )
        );
    }

    // TODO implement some reflection-based utilities.
    private static String extractLocation(TrackedWaypoint waypoint) {
        try {
            Class<?> clazz = waypoint.getClass();
            if (clazz.getSimpleName().equals("Vec3iWaypoint")) {
                var field = clazz.getDeclaredField("vector");
                field.setAccessible(true);
                Object vec = field.get(waypoint);
                return "Pos: " + vec;
            } else if (clazz.getSimpleName().equals("ChunkWaypoint")) {
                var field = clazz.getDeclaredField("chunkPos");
                field.setAccessible(true);
                Object chunk = field.get(waypoint);
                return "Chunk: " + chunk;
            } else if (clazz.getSimpleName().equals("AzimuthWaypoint")) {
                var field = clazz.getDeclaredField("angle");
                field.setAccessible(true);
                Object angle = field.get(waypoint);
                return "Azimuth: " + angle;
            }
        } catch (Exception ignored) {}
        return "N/A";
    }
}
