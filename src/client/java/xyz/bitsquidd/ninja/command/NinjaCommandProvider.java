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
import xyz.bitsquidd.bits.util.reflection.ClassGraph;
import xyz.bitsquidd.bits.wrapper.collection.AddableSet;

import java.util.List;

@SuppressWarnings("unchecked")
public final class NinjaCommandProvider implements BitsCommandProvider {
    private static String COMMAND_PACKAGE = "xyz.bitsquidd.ninja.command.impl.command";
    private static String ARGUMENT_PACKAGE = "xyz.bitsquidd.ninja.command.impl.argument";


    @Override
    public AddableSet<BitsCommand> getCommands() {
        return BitsCommandProvider.super.getCommands().addAll((List<BitsCommand>)ClassGraph.Scanner
          .getClasses(COMMAND_PACKAGE, BitsCommand.class)
          .stream().map(ClassGraph.Instance::create)
          .toList()
        );
    }

    @Override
    public AddableSet<AbstractArgumentParser<?>> getArguments() {
        return BitsCommandProvider.super.getArguments().addAll((List<AbstractArgumentParser<?>>)ClassGraph.Scanner
          .getClasses(ARGUMENT_PACKAGE, AbstractArgumentParser.class)
          .stream().map(ClassGraph.Instance::create)
          .toList()
        );
    }

}
