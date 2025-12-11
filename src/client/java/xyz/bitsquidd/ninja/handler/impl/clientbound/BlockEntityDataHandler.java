package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;
import java.util.Objects;

public class BlockEntityDataHandler extends PacketHandler<@NotNull ClientboundBlockEntityDataPacket> {

    public BlockEntityDataHandler() {
        super(
              ClientboundBlockEntityDataPacket.class,
              "BlockEntityData",
              "Handles ClientboundBlockEntityDataPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundBlockEntityDataPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Position"), Component.text(packet.getPos().getX() + ", " + packet.getPos().getY() + ", " + packet.getPos().getZ())),
                    PacketInfoSegment.of(Component.text("Type"), Component.text(Objects.requireNonNullElse(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(packet.getType()), "Unknown").toString())),
                    PacketInfoSegment.of(Component.text("Tag"), Component.text(packet.getTag().toString()))
              )
        );
    }

}
