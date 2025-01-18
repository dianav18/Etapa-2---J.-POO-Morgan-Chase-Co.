package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.SavingsAccount;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.AddInterestTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * Adds interest to a savings account of a user.
 */
public final class AddInterestCommand implements CommandHandler {
    private final int timestamp;
    private final String accountIBAN;
    private double interestRate;

    private final List<User> users;

    /**
     * Instantiates a new Add interest command.
     *
     * @param timestamp    the timestamp at which the interest is added
     * @param accountIBAN  the account iban
     * @param interestRate the interest rate
     * @param users        the users
     */
    public AddInterestCommand(final int timestamp, final String accountIBAN,
                              final double interestRate, final List<User> users) {
        this.timestamp = timestamp;
        this.accountIBAN = accountIBAN;
        this.interestRate = interestRate;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean accountIsSavinAccount = false;
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(accountIBAN)) {
                    if (account.getType().equals("savings")) {
                        accountIsSavinAccount = true;
                        final SavingsAccount savingsAccount = (SavingsAccount) account;
                        savingsAccount.addTransaction(new AddInterestTransaction(savingsAccount.getBalance() *
                                savingsAccount.getInterestRate() ,account.getCurrency(),
                                "Interest rate income", timestamp));
                        savingsAccount.addInterest(interestRate);
                        System.out.println(interestRate);

//                        savingsAccount.setInterestRate(interestRate);
//                        System.out.println(interestRate);
                        return;
                    }
                }
            }
        }
        if (!accountIsSavinAccount) {
            final ObjectNode outputNode = output.addObject();
            outputNode.put("command", "addInterest");
            outputNode.put("timestamp", timestamp);
            final ObjectNode outputNode2 = outputNode.putObject("output");
            outputNode2.put("description", "This is not a savings account");
            outputNode2.put("timestamp", timestamp);
        }
    }
}
