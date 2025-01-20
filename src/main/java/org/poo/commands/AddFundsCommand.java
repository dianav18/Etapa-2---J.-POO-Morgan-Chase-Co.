package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

/**
 * Adds funds to an account of a user.
 */
@AllArgsConstructor
public final class AddFundsCommand implements CommandHandler {

    private final String email;
    private final String account;
    private final double amount;
    private final int timestamp;

    @Override
    public void execute(final ArrayNode output) {
        User user = Main.getUser(this.email);
        Account account = Main.getAccount(this.account);

        if (user == null || account == null) {
            return;
        }

        account.addBalance(user, amount, account.getCurrency());
    }
}
