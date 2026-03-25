package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfoBundle;
import xyz.bitsquidd.ninja.format.PacketInfoList;
import xyz.bitsquidd.ninja.format.PacketInfoRow;
import xyz.bitsquidd.ninja.format.PacketInfoSegment;
import xyz.bitsquidd.ninja.handler.PacketHandler;
import xyz.bitsquidd.ninja.handler.PacketType;

import java.util.ArrayList;
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
        List<PacketInfoSegment> passengers = Arrays.stream(packet.getPassengers())
              .limit(MAX_DISPLAYED_ENTRIES)
              .mapToObj(id -> PacketInfoSegment.of(Component.text("PassengerId"), Component.text(id)))
              .toList();

        List<PacketInfoRow> rows = new ArrayList<>();
        rows.add(PacketInfoSegment.of(Component.text("VehicleId"), Component.text(packet.getVehicle())));
        rows.add(PacketInfoSegment.of(Component.text("PassengerCount"), Component.text(packet.getPassengers().length)));

        if (!passengers.isEmpty()) {
            rows.add(PacketInfoList.of(Component.text("Passengers"), passengers));
        }

        int hiddenPassengers = packet.getPassengers().length - passengers.size();
        if (hiddenPassengers > 0) {
            rows.add(PacketInfoSegment.of(Component.text("PassengersHidden"), Component.text(hiddenPassengers + " more")));
        }

        return PacketInfoBundle.ofRows(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }

}