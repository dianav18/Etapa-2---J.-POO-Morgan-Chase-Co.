package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.WithdrawSavingsTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;
import org.poo.handlers.GetClassicAccount;
import org.poo.handlers.GetUserAge;

import java.sql.SQLOutput;
import java.util.List;

public class WithdrawSavingsCommand implements CommandHandler {
    private final String command;
    private final String accountIban;
    private final double amount;
    private final String currency;
    private final int timestamp;

    private final List<User> users;
    private final CurrencyConverter currencyConverter;

    public WithdrawSavingsCommand(final String command, final String accountIban, final double amount,
                                  final String currency, final int timestamp, final List<User> users,
                                  final CurrencyConverter currencyConverter) {
        this.command = command;
        this.accountIban = accountIban;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.users = users;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            for (final Account account :user.getAccounts()) {
                if (account.getAccountIBAN().equals(accountIban)) {
                    if (GetUserAge.getUserAge(user.getBirthDate()) < 21) {
                        account.addTransaction(new WithdrawSavingsTransaction(timestamp,
                                "You don't have the minimum age required.", amount));
                        return;
                    }
                    if (!account.getType().equals("savings")) {
                        account.addTransaction(new WithdrawSavingsTransaction(timestamp,
                                "Account is not of type savings.", amount));
                        return;
                    }
                    if (account.getBalance() < amount) {
                        account.addTransaction(new WithdrawSavingsTransaction(timestamp,
                                "Insufficient funds", amount));
                        return;
                    }
                    final Account classicAccount = GetClassicAccount.getClassicAccount(users, accountIban);
                    if (classicAccount == null) {
                        account.addTransaction(new WithdrawSavingsTransaction(timestamp,
                                "You do not have a classic account.", amount));
                        return;
                    }
                }
            }
        }

    }
}
