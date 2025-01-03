package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * It sets a minimum balance to an account, which is used to check if the account
 * has enough money to perform a transaction.
 * It implements the CommandHandler interface.
 * It contains the amount, account IBAN, timestamp and users.
 */
public final class SetMinBalanceCommand implements CommandHandler {
    private final double amount;
    private final String accountIBAN;
    private final int timestamp;

    private List<User> users;

    /**
     * Instantiates a new Set min balance command.
     *
     * @param amount      the amount to be set
     * @param accountIBAN the account iban for which the min balance is set
     * @param timestamp   the timestamp at which the min balance is set
     * @param users       the users
     */
    public SetMinBalanceCommand(final double amount, final String accountIBAN,
                                final int timestamp, final List<User> users) {
        this.amount = amount;
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final var user : users) {
            for (final var account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(accountIBAN)) {
                    account.setMinBalance(amount);
                    return;
                }
            }
        }
    }
}
