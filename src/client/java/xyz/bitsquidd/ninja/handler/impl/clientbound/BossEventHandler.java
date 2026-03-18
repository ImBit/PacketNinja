package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BossEventHandler extends PacketHandler<@NotNull ClientboundBossEventPacket> {

    public BossEventHandler() {
        super(
              ClientboundBossEventPacket.class,
              "BossEvent",
              "Handles boss bar events",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ClientboundBossEventPacket packet) {
        List<PacketInfoSegment> segments = new ArrayList<>();

        try {
            Field idField = packet.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            UUID id = (UUID)idField.get(packet);
            segments.add(PacketInfoSegment.of(Component.text("Boss ID"), Component.text(id.toString())));

            Field operationField = packet.getClass().getDeclaredField("operation");
            operationField.setAccessible(true);
            Object operation = operationField.get(packet);

            String operationType = operation.getClass().getSimpleName();
            segments.add(PacketInfoSegment.of(Component.text("Operation"), Component.text(operationType)));

            extractOperationDetails(operation, segments);
        } catch (Exception ignored) {
            segments.add(PacketInfoSegment.of(Component.text("Error"), Component.text("Failed to extract data")));
        }

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              segments
        );
    }

    private void extractOperationDetails(Object operation, List<PacketInfoSegment> segments) {
        try {
            Field typeField = operation.getClass().getDeclaredField("type");
            typeField.setAccessible(true);

            Method getTypeMethod = operation.getClass().getMethod("getType");
            Enum<?> opType = (Enum<?>)getTypeMethod.invoke(operation);

            switch (opType.name()) {
                case "ADD" -> extractAddOperation(operation, segments);
                case "UPDATE_PROGRESS" -> extractProgressOperation(operation, segments);
                case "UPDATE_NAME" -> extractNameOperation(operation, segments);
                case "UPDATE_STYLE" -> extractStyleOperation(operation, segments);
                case "UPDATE_PROPERTIES" -> extractPropertiesOperation(operation, segments);
            }
        } catch (Exception ignored) {}
    }

    private void extractAddOperation(Object operation, List<PacketInfoSegment> segments) throws Exception {
        Field nameField = operation.getClass().getDeclaredField("name");
        nameField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Name"), Component.text(nameField.get(operation).toString())));

        Field progressField = operation.getClass().getDeclaredField("progress");
        progressField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Progress"), Component.text(String.valueOf(progressField.get(operation)))));

        Field colorField = operation.getClass().getDeclaredField("color");
        colorField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Color"), Component.text(colorField.get(operation).toString())));

        Field overlayField = operation.getClass().getDeclaredField("overlay");
        overlayField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Overlay"), Component.text(overlayField.get(operation).toString())));

        extractBooleanFlags(operation, segments);
    }

    private void extractProgressOperation(Object operation, List<PacketInfoSegment> segments) throws Exception {
        Field progressField = operation.getClass().getDeclaredField("progress");
        progressField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Progress"), Component.text(String.valueOf(progressField.get(operation)))));
    }

    private void extractNameOperation(Object operation, List<PacketInfoSegment> segments) throws Exception {
        Field nameField = operation.getClass().getDeclaredField("name");
        nameField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Name"), Component.text(nameField.get(operation).toString())));
    }

    private void extractStyleOperation(Object operation, List<PacketInfoSegment> segments) throws Exception {
        Field colorField = operation.getClass().getDeclaredField("color");
        colorField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Color"), Component.text(colorField.get(operation).toString())));

        Field overlayField = operation.getClass().getDeclaredField("overlay");
        overlayField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Overlay"), Component.text(overlayField.get(operation).toString())));
    }

    private void extractPropertiesOperation(Object operation, List<PacketInfoSegment> segments) throws Exception {
        extractBooleanFlags(operation, segments);
    }

    private void extractBooleanFlags(Object operation, List<PacketInfoSegment> segments) throws Exception {
        Field darkenField = operation.getClass().getDeclaredField("darkenScreen");
        darkenField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Darken Screen"), Component.text(String.valueOf(darkenField.get(operation)))));

        Field musicField = operation.getClass().getDeclaredField("playMusic");
        musicField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Play Music"), Component.text(String.valueOf(musicField.get(operation)))));

        Field fogField = operation.getClass().getDeclaredField("createWorldFog");
        fogField.setAccessible(true);
        segments.add(PacketInfoSegment.of(Component.text("Create Fog"), Component.text(String.valueOf(fogField.get(operation)))));
    }

}
