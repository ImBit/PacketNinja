package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class CustomPayloadHandler extends PacketHandler<@NotNull ClientboundCustomPayloadPacket> {

    public CustomPayloadHandler() {
        super(
              ClientboundCustomPayloadPacket.class,
              "CustomPayload",
              "Handles custom payload packets",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundCustomPayloadPacket packet) {
        CustomPacketPayload payload = packet.payload();

        CustomPacketPayload.Type<?> type = payload.type();
        ResourceLocation typeId = type.id();

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Type"), Component.text(typeId.getNamespace() + ":" + typeId.getPath())),
                    PacketInfoSegment.of(Component.text("Path"), Component.text(typeId.getPath())),
                    PacketInfoSegment.of(Component.text("PayloadClass"), Component.text(payload.getClass().getSimpleName())),
                    PacketInfoSegment.of(Component.text("PayloadString"), Component.text(payload.toString()))
              )
        );
    }

}