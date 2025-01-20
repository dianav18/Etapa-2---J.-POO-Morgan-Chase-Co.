package org.poo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Main;

/**
 * The type Utils.
 */
public final class Utils {

    private Utils() {

    }

    /**
     * Generic.
     *
     * @param output      the output
     * @param command     the command
     * @param description the description
     */
    public static void generic(final ArrayNode output, final String command,
                               final String description) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", command);
        commandOutput.put("timestamp", Main.getTimestamp());
        final ObjectNode errorDetails = commandOutput.putObject("output");
        errorDetails.put("description", description);
        errorDetails.put("timestamp", Main.getTimestamp());
    }

    /**
     * User not found.
     *
     * @param output  the output
     * @param command the command
     */
    public static void userNotFound(final ArrayNode output, final String command) {
        generic(output, command, "User not found");
    }

    /**
     * Account not found.
     *
     * @param output  the output
     * @param command the command
     */
    public static void accountNotFound(final ArrayNode output, final String command) {
        generic(output, command, "Account not found");
    }

    /**
     * Card not found.
     *
     * @param output  the output
     * @param command the command
     */
    public static void cardNotFound(final ArrayNode output, final String command) {
        generic(output, command, "Card not found");
    }

    /**
     * Not a business account.
     *
     * @param output  the output
     * @param command the command
     */
    public static void notABusinessAccount(final ArrayNode output, final String command) {
        generic(output, command, "This is not a business account");
    }

}
