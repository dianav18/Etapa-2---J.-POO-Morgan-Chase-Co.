package org.poo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.main.Main;

public class Utils {

    public static void generic(final ArrayNode output, String command, String description) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", command);
        commandOutput.put("timestamp", Main.getTimestamp());
        final ObjectNode errorDetails = commandOutput.putObject("output");
        errorDetails.put("description", description);
        errorDetails.put("timestamp", Main.getTimestamp());
    }
    public static void userNotFound(final ArrayNode output, String command) {
        generic(output, command, "User not found");
    }

    public static void accountNotFound(final ArrayNode output, String command) {
        generic(output, command, "Account not found");
    }

    public static void cardNotFound(final ArrayNode output, String command) {
        generic(output, command, "Card not found");
    }

    public static void notABusinessAccount(final ArrayNode output, String command) {
        generic(output, command, "This is not a business account");
    }

}
