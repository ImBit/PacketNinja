package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class ChangeDifficultyHandler extends PacketHandler<@NotNull ClientboundChangeDifficultyPacket> {

    public ChangeDifficultyHandler() {
        super(
              ClientboundChangeDifficultyPacket.class,
              "ChangeDifficulty",
              "Handles ClientboundChangeDifficultyPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundChangeDifficultyPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Difficulty"), Component.text(packet.difficulty().name())),
                    PacketInfoSegment.of(Component.text("Locked"), Component.text(packet.locked()))
              )
        );
    }

}
