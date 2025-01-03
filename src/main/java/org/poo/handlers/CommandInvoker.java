package org.poo.handlers;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Command invoker.
 * It contains the addCommand and executeCommands methods.
 * It is used to add a command and execute all the commands.
 */
public class CommandInvoker {
    private final List<CommandHandler> commands = new ArrayList<>();

    /**
     * Add command.
     *
     * @param command the command to be added
     */
    public void addCommand(final CommandHandler command) {
        commands.add(command);
    }

    /**
     * Execute commands.
     *
     * @param output the output array node to
     *               which the result is added after the command is executed
     */
    public void executeCommands(final ArrayNode output) {
        for (final CommandHandler command : commands) {
            command.execute(output);
        }
        commands.clear();
    }
}
