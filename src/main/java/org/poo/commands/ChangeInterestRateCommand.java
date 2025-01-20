package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.Utils;
import org.poo.bankInput.Account;
import org.poo.bankInput.SavingsAccount;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.ChangeInterestRateTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

import java.util.List;

/**
 * Changes the interest rate of an account of a user.
 */
public final class ChangeInterestRateCommand implements CommandHandler {
    private final String accountIBAN;
    private final double newInterestRate;
    private final int timestamp;

    private final List<User> users;

    /**
     * Instantiates a new Change interest rate command.
     *
     * @param accountIBAN     the account iban
     * @param newInterestRate the new interest rate
     * @param timestamp       the timestamp
     * @param users           the users
     */
    public ChangeInterestRateCommand(final String accountIBAN, final double newInterestRate,
                                     final int timestamp, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.newInterestRate = newInterestRate;
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
        final Account account = Main.getAccount(accountIBAN);

        if (account == null) {
            Utils.accountNotFound(output, "changeInterest");
            return;
        }

        if (!account.getType().equals("savings")) {
            final ObjectNode outputNode = output.addObject();
            outputNode.put("command", "changeInterestRate");
            outputNode.put("timestamp", timestamp);
            final ObjectNode outputNode2 = outputNode.putObject("output");
            outputNode2.put("description", "This is not a savings account");
            outputNode2.put("timestamp", timestamp);
        }

        final SavingsAccount savingsAccount = (SavingsAccount) account;
        savingsAccount.addTransaction(new ChangeInterestRateTransaction(timestamp, newInterestRate,
                "Interest rate of the account changed to " + newInterestRate));
        savingsAccount.setInterestRate(newInterestRate);
    }
}
