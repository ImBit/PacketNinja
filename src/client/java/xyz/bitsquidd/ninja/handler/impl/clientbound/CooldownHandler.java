package xyz.bitsquidd.ninja.handler.impl.clientbound;

import com.mojang.brigadier.suggestion.Suggestion;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class CooldownHandler extends PacketHandler<@NotNull ClientboundCooldownPacket> {

    public CooldownHandler() {
        super(
              ClientboundCooldownPacket.class,
              "Cooldown",
              "Handles ClientboundCooldownPacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundCooldownPacket packet) {

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Duration"), Component.text(packet.cooldownGroup().toString())),
                    PacketInfoSegment.of(Component.text("Duration"), Component.text(packet.duration()))
              )
        );
    }

}
