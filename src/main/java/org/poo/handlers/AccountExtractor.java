package org.poo.handlers;

import org.poo.bankInput.Account;
import org.poo.bankInput.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Account extractor.
 * It extracts accounts from users list.
 */
public final class AccountExtractor {
    /**
     * Private constructor to prevent instantiation.
     */
    private AccountExtractor() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Extract accounts from users list.
     *
     * @param users the users list
     * @return the list of accounts
     */
    public static List<Account> extractAccountsFromUsers(final List<User> users) {
        final List<Account> accounts = new ArrayList<>();
        for (final User user : users) {
            accounts.addAll(user.getAccounts());
        }
        return accounts;
    }
}
