package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.AccountWarningTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * Checks the status of a card. If the account balance is below the
 * minimum balance, the card is frozen.
 * If the card is not found, an error is displayed.
 */
public final class CheckCardStatusCommand implements CommandHandler {
    private final String cardNumber;
    private final int timestamp;
    private final List<User> users;

    /**
     * Instantiates a new Check card status command.
     *
     * @param cardNumber the card number to be checked
     * @param timestamp  the timestamp at which the card is checked
     * @param users      the users
     */
    public CheckCardStatusCommand(final String cardNumber, final int timestamp,
                                  final List<User> users) {
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
        this.users = users;
    }

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        boolean cardFound = false;

        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                for (final Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        cardFound = true;
                        if (account.getBalance() <= account.getMinBalance()) {
                            card.freeze();
                            account.addTransaction(new AccountWarningTransaction(timestamp,
                                    "You have reached the minimum amount of funds"));
                            return;
                        }
                    }
                }
            }
        }

        if (!cardFound) {
            final ObjectNode errorOutput = output.addObject();
            errorOutput.put("command", "checkCardStatus");
            errorOutput.put("timestamp", timestamp);
            final ObjectNode errorDetails = errorOutput.putObject("output");
            errorDetails.put("description", "Card not found");
            errorDetails.put("timestamp", timestamp);
        }
    }
}
