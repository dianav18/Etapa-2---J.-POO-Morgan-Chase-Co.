package org.poo.commands;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * Adds funds to an account of a user.
 */
public final class AddFundsCommand implements CommandHandler {
    private final String account;
    private final double amount;
    private final int timestamp;
    private final List<User> users;

    /**
     * Instantiates a new Add funds command.
     *
     * @param account   the account iban
     * @param amount    the amount to be added
     * @param timestamp the timestamp at which the funds are added
     * @param users     the users
     */
    public AddFundsCommand(final String account, final double amount,
                           final int timestamp, final List<User> users) {
        this.account = account;
        this.amount = amount;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            for (final Account userAccount : user.getAccounts()) {
                if (userAccount.getAccountIBAN().equals(account)) {
                    userAccount.setBalance(userAccount.getBalance() + amount);
                }
            }
        }
    }
}
