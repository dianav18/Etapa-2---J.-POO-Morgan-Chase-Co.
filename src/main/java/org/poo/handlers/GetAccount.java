package org.poo.handlers;

import org.poo.bankInput.Account;
import org.poo.bankInput.User;

import java.util.List;

public class GetAccount {
    private final String iban;
    private final List<User> users;

    public GetAccount(final String iban, final List<User> users) {
        this.iban = iban;
        this.users = users;
    }

    public Account getAccount() {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(iban)) {
                    return account;
                }
            }
        }
        return null;
    }
}
