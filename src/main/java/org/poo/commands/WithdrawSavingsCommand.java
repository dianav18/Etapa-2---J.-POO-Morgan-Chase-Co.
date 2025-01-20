package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.Utils;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.GenericTransaction;
import org.poo.bankInput.transactions.InsufficientFundsTransaction;
import org.poo.bankInput.transactions.NoClassicAccountTransaction;
import org.poo.bankInput.transactions.WithdrawSavingsTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.GetUserAge;
import org.poo.main.Main;

/**
 * The type Withdraw savings command.
 */
@AllArgsConstructor
public class WithdrawSavingsCommand implements CommandHandler {
    private final String accountIban;
    private final double amount;
    private final String currency;
    private final int timestamp;

    private static final int AGE_OF_CONSENT = 21;

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final Account account = Main.getAccount(accountIban);

        if (account == null) {
            Utils.accountNotFound(output, "withdrawSavings");
            return;
        }

        final User user = account.getOwner();

        if (GetUserAge.getUserAge(user.getBirthDate()) < AGE_OF_CONSENT) {
            account.addTransaction(new GenericTransaction(
                    "You don't have the minimum age required."));
            return;
        }

        if (!account.getType().equals("savings")) {
            account.addTransaction(new GenericTransaction("Account is not of type savings."));
            return;
        }

        Account classicAccount = null;

        for (final Account userAccount : user.getAccounts()) {
            if (userAccount.getType().equals("classic")) {
                classicAccount = userAccount;
            }
        }

        if (classicAccount == null) {
            account.addTransaction(new NoClassicAccountTransaction(timestamp));
            return;
        }

        if (account.getBalance() < amount) {
            account.addTransaction(new GenericTransaction("Insufficient funds"));
            return;
        }

        if (!account.removeBalance(user, amount, 0, 0)) {
            account.addTransaction(new InsufficientFundsTransaction(timestamp));
            return;
        }

        classicAccount.addBalance(user, amount, currency);

        account.addTransaction(new WithdrawSavingsTransaction(timestamp, amount,
                classicAccount.getAccountIBAN(), accountIban));
        classicAccount.addTransaction(new WithdrawSavingsTransaction(timestamp, amount,
                classicAccount.getAccountIBAN(), accountIban));
    }
}
