package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
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
        Identifier typeId = type.id();

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfo.data(Component.text("Type"), Component.text(typeId.getNamespace() + ":" + typeId.getPath())),
                    PacketInfo.data(Component.text("Path"), Component.text(typeId.getPath())),
                    PacketInfo.data(Component.text("PayloadClass"), Component.text(payload.getClass().getSimpleName())),
                    PacketInfo.data(Component.text("PayloadString"), Component.text(payload.toString()))
              )
        );
    }

}