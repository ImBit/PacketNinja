package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundDebugEventPacket;
import net.minecraft.network.protocol.game.ClientboundDebugSamplePacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.Arrays;
import java.util.List;

public class DebugSampleHandler extends PacketHandler<@NotNull ClientboundDebugSamplePacket> {

    public DebugSampleHandler() {
        super(
              ClientboundDebugSamplePacket.class,
              "DebugSample",
              "Handles ClientboundDebugSamplePacket.",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundDebugSamplePacket packet) {
        String sampleString = FormatHelper.formatList(List.of(packet.sample()), MAX_DISPLAYED_ENTRIES);
        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("Sample"), Component.text(sampleString)),
                    PacketInfoSegment.of(Component.text("SampleType"), Component.text(packet.debugSampleType().toString()))
              )
        );
    }

}
