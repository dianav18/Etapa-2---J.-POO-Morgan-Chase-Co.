package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CannotDeleteAccountTransaction;
import org.poo.bankInput.transactions.CardDestroyedTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

import java.util.List;

/**
 * It deletes an account from a user.
 */
public final class DeleteAccountCommand implements CommandHandler {
    private final String accountIBAN;
    private final int timestamp;
    private final String email;
    private final List<User> users;

    /**
     * Instantiates a new Delete account command.
     *
     * @param accountIBAN the account iban
     * @param timestamp   the timestamp at which the account is deleted
     * @param email       the email of the user
     * @param users       the users
     */
    public DeleteAccountCommand(final String accountIBAN, final int timestamp,
                                final String email, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.email = email;
        this.users = users;
    }

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final User user = Main.getUser(email);
        final Account account = Main.getAccount(accountIBAN);

        if (user == null || account == null) {
            return;
        }

        if (account.getBalance() != 0) {
            account.addTransaction(new CannotDeleteAccountTransaction(
                    timestamp,
                    "The account couldn't be deleted because it has"
                            + "a balance greater than 0"
            ));
            outputError(output);
            return;
        }

        for (final Card card : account.getCards()) {
            card.destroy();
            account.addTransaction(new CardDestroyedTransaction(
                    timestamp,
                    "The card has been destroyed",
                    accountIBAN,
                    card.getCardNumber(),
                    email
            ));
        }
        user.removeAccount(account);
        outputPrint(output);
    }

    private void outputError(final ArrayNode output) {
        final ObjectNode errorOutput = output.addObject();
        errorOutput.put("command", "deleteAccount");
        errorOutput.put("timestamp", timestamp);
        final ObjectNode errorDetails = errorOutput.putObject("output");
        errorDetails.put("error",
                "Account couldn't be deleted - see org.poo.transactions for details");
        errorDetails.put("timestamp", timestamp);
    }

    private void outputPrint(final ArrayNode output) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "deleteAccount");
        commandOutput.put("timestamp", timestamp);
        final ObjectNode successDetails = commandOutput.putObject("output");
        successDetails.put("success", "Account deleted");
        successDetails.put("timestamp", timestamp);
    }
}
