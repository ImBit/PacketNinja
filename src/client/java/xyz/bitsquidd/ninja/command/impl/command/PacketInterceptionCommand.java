/*
 * This file is part of a Bit libraries package.
 * Licensed under the GNU Lesser General Public License v3.0.
 *
 * Copyright (c) 2023-2026 ImBit
 */

package xyz.bitsquidd.ninja.command.impl.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

import xyz.bitsquidd.bits.command.BitsCommand;
import xyz.bitsquidd.bits.command.annotation.Command;
import xyz.bitsquidd.bits.fabric.command.FabricClientBitsCommandContext;
import xyz.bitsquidd.bits.sendable.text.Text;
import xyz.bitsquidd.ninja.PacketFilter;
import xyz.bitsquidd.ninja.PacketInterceptorMod;
import xyz.bitsquidd.ninja.PacketRegistry;
import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.util.List;

@SuppressWarnings("rawtypes")
@Command("packets")
public class PacketInterceptionCommand extends BitsCommand {

    @Command("start")
    public void startLogging(FabricClientBitsCommandContext ctx) {
        PacketInterceptorMod.logPackets = true;

        ctx.respond(Text.of(
          Component.text("Started packet logging...")
        ));
    }

    @Command("stop")
    public void stopLogging(FabricClientBitsCommandContext ctx) {
        PacketInterceptorMod.logPackets = false;

        ctx.respond(Text.of(
          Component.text("Stopped packet logging...")
        ));
    }

    @Command("filter")
    public static class Filter extends BitsCommand {
        @Command("list")
        public void listPackets(FabricClientBitsCommandContext ctx) {
            PacketFilter filter = PacketInterceptorMod.getInstance().getPacketFilter();

            ctx.respond(Text.of(
              Component.empty()
                .append(Component.text("Logging: ").decorate(TextDecoration.BOLD))
                .append(Component.text(PacketInterceptorMod.logPackets ? "ON" : "OFF"))
            ));

            List<PacketHandler<?>> enabledHandlers = PacketRegistry.getAllHandlers().stream()
              .filter(handler -> filter.isPacketEnabled(handler.getPacketClass()))
              .toList();
            List<PacketHandler<?>> disabledHandlers = PacketRegistry.getAllHandlers().stream()
              .filter(handler -> !filter.isPacketEnabled(handler.getPacketClass()))
              .toList();

            if (!enabledHandlers.isEmpty()) {
                ctx.respond(Text.of(
                  Component.text("Enabled: ").decorate(TextDecoration.BOLD, TextDecoration.UNDERLINED)
                ));
                for (PacketHandler<?> handler : enabledHandlers) {
                    ctx.respond(Text.of(
                      Component.empty()
                        .append(Component.text(" " + handler.getFriendlyName() + ": ").decorate(TextDecoration.BOLD))
                        .append(Component.text(handler.getDescription()))
                    ));
                }
            }

            if (!disabledHandlers.isEmpty()) {
                ctx.respond(Text.of(
                  Component.text("Disabled: ").decorate(TextDecoration.BOLD, TextDecoration.UNDERLINED)
                ));
                for (PacketHandler<?> handler : disabledHandlers) {
                    ctx.respond(Text.of(
                      Component.empty()
                        .append(Component.text(" " + handler.getFriendlyName() + ": ").decorate(TextDecoration.BOLD))
                        .append(Component.text(handler.getDescription()))
                    ));
                }
            }
        }

        @Command("toggle")
        public void togglePacket(FabricClientBitsCommandContext ctx, PacketHandler handler) {
            PacketFilter filter = PacketInterceptorMod.getInstance().getPacketFilter();

            filter.togglePacketFilter(handler);
            boolean isEnabled = filter.isPacketEnabled(handler.getPacketClass());

            if (isEnabled) {
                ctx.respond(Text.of(Component.empty()
                    .append(Component.text("Enabled interception for: ").decorate(TextDecoration.BOLD))
                    .append(Component.text(handler.getFriendlyName()))
                  )
                );
            } else {
                ctx.respond(Text.of(Component.empty()
                    .append(Component.text("Disabled interception for: ").decorate(TextDecoration.BOLD))
                    .append(Component.text(handler.getFriendlyName()))
                  )
                );
            }

        }

    }

}
