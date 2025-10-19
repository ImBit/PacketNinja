package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import org.jetbrains.annotations.NotNull;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class AddEntityHandler extends PacketHandler<@NotNull ClientboundAddEntityPacket> {

    public AddEntityHandler() {
        super(
                ClientboundAddEntityPacket.class,
                "AddEntity",
                "Handles entity spawning",
                PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundAddEntityPacket packet) {
        return PacketInfoBundle.of(
                packetType,
                Component.text(friendlyName),
                List.of(
                        PacketInfoSegment.of(Component.text("EntityID"), Component.text(packet.getId())),
                        PacketInfoSegment.of(Component.text("UUID"), Component.text(packet.getUUID().toString())),
                        PacketInfoSegment.of(Component.text("Pos"), Component.text("["+String.format("%.2f", packet.getX())+","+String.format("%.2f", packet.getY())+","+String.format("%.2f", packet.getZ())+"]")),
                        PacketInfoSegment.of(Component.text("Rotation"), Component.text("["+String.format("%.2f", packet.getXRot())+","+String.format("%.2f", packet.getYRot())+"]"))
                )
        );
    }

}