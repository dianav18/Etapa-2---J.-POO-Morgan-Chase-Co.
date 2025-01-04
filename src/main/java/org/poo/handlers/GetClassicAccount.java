package org.poo.handlers;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;

import java.util.List;

public class GetClassicAccount {
    public static Account getClassicAccount(final List <User> users, final String iban) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(iban)) {
                    if (account.getType().equals("classic")) {
                        return account;
                    }
                }
            }
        }
        return null;
    }
}
