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

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final User user = Main.getUser(this.email);
        final Account localAccount = Main.getAccount(this.account);

        if (user == null || localAccount == null) {
            return;
        }

        localAccount.addBalance(user, amount, localAccount.getCurrency());
    }
}
