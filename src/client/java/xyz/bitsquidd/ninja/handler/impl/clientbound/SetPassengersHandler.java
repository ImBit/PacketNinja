package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.FormatHelper;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.Arrays;
import java.util.List;

public class SetPassengersHandler extends PacketHandler<@NotNull ClientboundSetPassengersPacket> {

    public SetPassengersHandler() {
        super(
              ClientboundSetPassengersPacket.class,
              "SetPassengers",
              "Handles entity riding/dismounting",
              PacketType.CLIENTBOUND
        );
    }

    @Override
    protected @NotNull PacketInfoBundle getPacketInfoInternal(ClientboundSetPassengersPacket packet) {
        String passengerIdList = FormatHelper.formatList(packet.getPassengers().length > 0 ? Arrays.stream(packet.getPassengers()).boxed().toList() : List.of(), MAX_DISPLAYED_ENTRIES);

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              List.of(
                    PacketInfoSegment.of(Component.text("VehicleId"), Component.text(packet.getVehicle())),
                    PacketInfoSegment.of(Component.text("PassengerCount"), Component.text(packet.getPassengers().length)),
                    PacketInfoSegment.of(Component.text("Passengers"), Component.text(passengerIdList))
              )
        );
    }

}