package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.Transaction;
import org.poo.bankInput.transactions.TransactionPrinter;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

import java.util.List;

/**
 * It represents the print transactions command,
 * which is used to print the transactions of a user.
 */
public final class PrintTransactionsCommand implements CommandHandler {
    private final String email;
    private final int timestamp;
    private final List<User> users;

    /**
     * Instantiates a new Print transactions command.
     *
     * @param email     the email of the user
     * @param timestamp the timestamp at which the transactions are printed
     * @param users     the users
     */
    public PrintTransactionsCommand(final String email, final int timestamp,
                                    final List<User> users) {
        this.email = email;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        User user = Main.getUser(email);

        if (user == null) {
            return;
        }

        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "printTransactions");
        commandOutput.put("timestamp", timestamp);

        final ArrayNode transactionsOutput = commandOutput.putArray("output");

        final TransactionPrinter printer = new TransactionPrinter(transactionsOutput);

        for (final Transaction transaction : user.getTransactions()) {
            transaction.accept(printer);
        }
    }

}
