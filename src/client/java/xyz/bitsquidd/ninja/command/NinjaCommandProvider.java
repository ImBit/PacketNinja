/*
 * This file is part of a Bit libraries package.
 * Licensed under the GNU Lesser General Public License v3.0.
 *
 * Copyright (c) 2023-2026 ImBit
 */

package xyz.bitsquidd.ninja.command;

import xyz.bitsquidd.bits.mc.command.BitsCommand;
import xyz.bitsquidd.bits.mc.command.argument.parser.AbstractArgumentParser;
import xyz.bitsquidd.bits.mc.command.provider.BitsCommandProvider;
import xyz.bitsquidd.bits.util.reflection.ReflectionUtils;
import xyz.bitsquidd.bits.util.reflection.ScannerFlags;
import xyz.bitsquidd.bits.wrapper.collection.AddableSet;

public final class NinjaCommandProvider implements BitsCommandProvider {
    private static String COMMAND_PACKAGE = "xyz.bitsquidd.ninja.command.impl.command";
    private static String ARGUMENT_PACKAGE = "xyz.bitsquidd.ninja.command.impl.argument";


    @Override
    public AddableSet<BitsCommand> getCommands() {
        return BitsCommandProvider.super.getCommands().addAll(ReflectionUtils.General
          .createClassesInDir(COMMAND_PACKAGE, BitsCommand.class, ScannerFlags.DEFAULT)
          .stream().toList()
        );
    }

    @Override
    public AddableSet<AbstractArgumentParser<?>> getArguments() {
        return BitsCommandProvider.super.getArguments().addAll(ReflectionUtils.General
          .createClassesInDir(ARGUMENT_PACKAGE, AbstractArgumentParser.class, ScannerFlags.DEFAULT)
          .stream()
          .map(parser -> (AbstractArgumentParser<?>)parser)
          .toList()
        );
    }

}
