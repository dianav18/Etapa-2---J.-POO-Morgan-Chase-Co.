package org.poo.handlers;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * The interface Command handler.
 * It is used to execute a command.
 * It contains the execute method.
 * It is implemented by the classes that represent the commands.
 */
public interface CommandHandler {
    /**
     * Execute.
     *
     * @param output the output array node to which the
     *               result is added after the command is executed
     */
    void execute(ArrayNode output);
}
