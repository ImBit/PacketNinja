package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;
import java.util.Objects;

public class BlockEventHandler extends PacketHandler<@NotNull ClientboundBlockEventPacket> {

    public BlockEventHandler() {
        super(
              ClientboundBlockEventPacket.class,
              "BlockEvent",
              "Handles ClientboundBlockDestructionPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundBlockEventPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Position"), Component.text(packet.getPos().getX() + ", " + packet.getPos().getY() + ", " + packet.getPos().getZ())),
                    PacketInfoSegment.of(Component.text("Friction"), Component.text(packet.getBlock().getFriction())),
                    PacketInfoSegment.of(Component.text("ExplosionResistance"), Component.text(packet.getBlock().getExplosionResistance())),
                    PacketInfoSegment.of(Component.text("JumpFactor"), Component.text(packet.getBlock().getJumpFactor())),
                    PacketInfoSegment.of(Component.text("SpeedFactor"), Component.text(packet.getBlock().getSpeedFactor())),
                    PacketInfoSegment.of(Component.text("B0"), Component.text(packet.getB0())),
                    PacketInfoSegment.of(Component.text("B1"), Component.text(packet.getB1()))
              )
        );
    }

}
