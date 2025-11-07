package xyz.bitsquidd.ninja.handler.impl.clientbound;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.jetbrains.annotations.NotNull;
import com.mojang.serialization.JsonOps;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.List;

public class SystemChatHandler extends PacketHandler<@NotNull ClientboundSystemChatPacket>  {
    public SystemChatHandler() {
        super(
                ClientboundSystemChatPacket.class,
                "SystemChat",
                "Handles system chat messages",
                PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(@NotNull ClientboundSystemChatPacket packet) {
        Gson gson = new Gson();
        final var jsonElement = ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, packet.content()).getOrThrow();
        final var jsonString = gson.toJson(jsonElement);

        return PacketInfoBundle.of(
                packetType,
                Component.text(friendlyName),
                List.of(
                        PacketInfoSegment.of(
                              Component.text("JSON Message"),
                              Component.text(jsonString)
                        )
                )
        );
    }
}
