package xyz.bitsquidd.ninja.handler.impl.clientbound;

import com.mojang.brigadier.tree.CommandNode;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class CommandsHandler extends PacketHandler<@NotNull ClientboundCommandsPacket> {

    public CommandsHandler() {
        super(
              ClientboundCommandsPacket.class,
              "Commands",
              "Handles ClientboundCommandsPacket.",
              PacketType.CLIENTBOUND
        );
    }

    // todo: maybe fetch the commands??
    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundCommandsPacket packet) {
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
              )
        );
    }

}
