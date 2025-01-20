package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * It sets an alias to a user's account.
 */
public final class SetAliasCommand implements CommandHandler {
    private final String email;
    private final String alias;
    private final String accountIBAN;

    private final List<User> users;

    /**
     * Instantiates a new Set alias command.
     *
     * @param email       the email of the user
     * @param alias       the alias to be set
     * @param accountIBAN the account iban for which the alias is set
     * @param users       the users
     */
    public SetAliasCommand(final String email, final String alias,
                           final String accountIBAN, final List<User> users) {
        this.email = email;
        this.alias = alias;
        this.accountIBAN = accountIBAN;
        this.users = users;
    }

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    if (account.getAccountIBAN().equals(accountIBAN)) {
                        account.setAlias(alias);
                    }
                }
            }
        }
    }
}
