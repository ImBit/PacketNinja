/*
 * This file is part of a Bit libraries package.
 * Licensed under the GNU Lesser General Public License v3.0.
 *
 * Copyright (c) 2023-2026 ImBit
 */

package xyz.bitsquidd.ninja.command.impl.argument;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import xyz.bitsquidd.bits.command.argument.parser.AbstractArgumentParser;
import xyz.bitsquidd.bits.command.exception.ExceptionBuilder;
import xyz.bitsquidd.bits.command.util.BitsCommandContext;
import xyz.bitsquidd.bits.wrapper.type.TypeSignature;
import xyz.bitsquidd.ninja.PacketRegistry;
import xyz.bitsquidd.ninja.handler.PacketHandler;

import java.util.List;
import java.util.function.Supplier;

public final class PacketHandlerArgumentParser extends AbstractArgumentParser<PacketHandler<?>> {

    public PacketHandlerArgumentParser() {
        super(TypeSignature.of(PacketHandler.class), "PacketHandler");
    }

    @Override
    public PacketHandler<?> parse(List<Object> inputObjects, BitsCommandContext<?> bitsCommandContext) throws CommandSyntaxException {
        String inputString = this.singletonInputValidation(inputObjects, String.class);

        return PacketRegistry.fromFriendlyName(inputString).orElseThrow(() ->
          ExceptionBuilder.createCommandException("Unknown packet handler name: " + inputString)
        );
    }

    @Override
    public Supplier<List<String>> getSuggestions() {
        return () -> PacketRegistry.getAllHandlers().stream().map(PacketHandler::getFriendlyName).toList();
    }

}
