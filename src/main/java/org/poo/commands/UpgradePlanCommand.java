package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.UpgradePlanTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;

import java.util.List;

public class UpgradePlanCommand implements CommandHandler {
    private final String command;
    private final String newPlanType;
    private final String accountIBAN;
    private final int timestamp;
    private final List<User> users;
    private final CurrencyConverter currencyConverter;

    public UpgradePlanCommand(final String command, final String newPlanType, final String accountIBAN,
                              final int timestamp, final List<User> users, final CurrencyConverter currencyConverter) {
        this.command = command;
        this.newPlanType = newPlanType;
        this.accountIBAN = accountIBAN;
        this.timestamp = timestamp;
        this.users = users;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(accountIBAN)) {
                    if (account.getType().equals(newPlanType)) {
                        System.out.println("The user already has the" + newPlanType + " plan."); //TODO
                        return;
                    }

                    final double amount;
                    //TODO: Insufficient funds
                    if (account.getCurrency().equals("RON")) {
                        amount = account.getBalance();
                    } else {
                        //TODO: Currency exchange
                        amount = currencyConverter.convert(account.getBalance(), account.getCurrency(), "RON");
                    }
                    //TODO: Account not found
                    final double upgradeFee = getUpgradeFee(account.getOwner().getPlan(), newPlanType);
                    final double finalUpgradeFee;
                    finalUpgradeFee = currencyConverter.convert(upgradeFee, "RON", account.getCurrency());
                    if (upgradeFee == -1) {
                        //TODO: You cannot downgrade your plan.
                        return;
                    }

                    // TODO check if the user has enought money

                    account.setBalance(account.getBalance() - finalUpgradeFee);
                    for (final Account userAccount : user.getAccounts()) {
                        userAccount.getOwner().setPlan(newPlanType);
                    }
                    account.addTransaction(new UpgradePlanTransaction(accountIBAN, "Upgrade plan",
                            newPlanType, timestamp));
                }
            }
        }
    }

    private double getUpgradeFee(final String currentPlan, final String newPlan) {
        if (currentPlan.equals("standard") || currentPlan.equals("student")) {
            if (newPlan.equals("silver")) return 100;
            if (newPlan.equals("gold")) return 350;
        }
        if (currentPlan.equals("silver") && newPlan.equals("gold")) return 250;
        return -1;
    }
}
