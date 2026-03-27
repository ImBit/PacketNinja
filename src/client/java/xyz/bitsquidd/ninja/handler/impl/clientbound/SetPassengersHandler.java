package xyz.bitsquidd.ninja.handler.impl.clientbound;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.jetbrains.annotations.NotNull;

import xyz.bitsquidd.ninja.format.PacketInfo;
import xyz.bitsquidd.ninja.format.PacketInfoBundle;
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
        List<PacketInfo> passengers = Arrays.stream(packet.getPassengers())
              .limit(MAX_DISPLAYED_ENTRIES)
              .mapToObj(id -> PacketInfo.data(Component.text("PassengerId"), Component.text(id)))
              .toList();

        List<PacketInfo> rows = new ArrayList<>();
        rows.add(PacketInfo.data(Component.text("VehicleId"), Component.text(packet.getVehicle())));
        rows.add(PacketInfo.data(Component.text("PassengerCount"), Component.text(packet.getPassengers().length)));

        if (!passengers.isEmpty()) {
            rows.add(PacketInfo.list(Component.text("Passengers"), passengers));
        }

        int hiddenPassengers = packet.getPassengers().length - passengers.size();
        if (hiddenPassengers > 0) {
            rows.add(PacketInfo.data(Component.text("PassengersHidden"), Component.text(hiddenPassengers + " more")));
        }

        return PacketInfoBundle.of(
              packetType,
              Component.text(friendlyName),
              rows
        );
    }

}