package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class OpenScreenHandler extends PacketHandler<@NotNull ClientboundOpenScreenPacket> {

    public OpenScreenHandler() {
        super(
              ClientboundOpenScreenPacket.class,
              "OpenScreen",
              "Handles opening of container screens.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundOpenScreenPacket packet) {
        MenuType<?> type = packet.getType();
        String typeDesc = type.toString();
        String titleDesc = packet.getTitle().toString();
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("ContainerID"), Component.text(packet.getContainerId())),
                    PacketInfoSegment.of(Component.text("MenuType"), Component.text(typeDesc)),
                    PacketInfoSegment.of(Component.text("Title"), Component.text(titleDesc))
              )
        );
    }

}