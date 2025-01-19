package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.*;
import org.poo.bankInput.transactions.InsufficientFundsTransaction;
import org.poo.bankInput.transactions.ReceivedTransaction;
import org.poo.bankInput.transactions.SentTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;
import org.poo.main.Main;

import java.util.List;

/**
 * It represents the send money command, which is used to send money from one account to another.
 */
public final class SendMoneyCommand implements CommandHandler {
    private final String senderIBAN;
    private final double amount;
    private final String receiverIBAN;
    private final int timestamp;
    private final String description;
    private double commission;
    private double cashback;

    private final List<Account> accounts;
    private final CurrencyConverter currencyConverter;

    private final List<User> users;


    /**
     * Instantiates a new Send money command.
     *
     * @param senderIBAN        the sender iban
     * @param amount            the amount to be sent
     * @param receiverIBAN      the receiver iban
     * @param timestamp         the timestamp at which the money is sent
     * @param description       the description of the transaction
     * @param accounts          the accounts
     * @param currencyConverter the currency converter
     * @param users             the users
     */
    public SendMoneyCommand(final String senderIBAN, final double amount,
                            final String receiverIBAN, final int timestamp,
                            final String description, final List<Account> accounts,
                            final CurrencyConverter currencyConverter,
                            final List<User> users) {
        this.senderIBAN = senderIBAN;
        this.amount = amount;
        this.receiverIBAN = receiverIBAN;
        this.timestamp = timestamp;
        this.description = description;
        this.accounts = accounts;
        this.currencyConverter = currencyConverter;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        Account senderAccount = null;
        Account receiverAccount = null;

        User senderUser = null;
        User receiverUser = null;

        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(senderIBAN)) {
                    senderAccount = account;
                    senderUser = user;
                }
                if (account.getAccountIBAN().equals(receiverIBAN)) {
                    receiverAccount = account;
                    receiverUser = user;
                }
            }
        }

        if (senderAccount == null || receiverAccount == null) {
            userNotFound(output);
            return;
        }

        if (senderAccount.getBalance() < amount) {
            senderAccount.addTransaction(new InsufficientFundsTransaction(timestamp,
                    "Insufficient funds"));
            return;
        }

        if (senderAccount.getBalance() <= senderAccount.getMinBalance()) {
            return;
        }

        double convertedAmount = amount;
        if (!senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
            convertedAmount = currencyConverter.convert(amount, senderAccount.getCurrency(),
                    receiverAccount.getCurrency());
            if (convertedAmount == -1) {
                return;
            }
        }

//        final double ronAmount = Main.getCurrencyConverter().convert(amount, senderAccount.getCurrency(), "RON");;

//        if (senderAccount.getTypeOfPlan().equals("standard")) {
//            commission = amount * 0.002;
//        } else if (senderAccount.getTypeOfPlan().equals("student")) {
//            commission = 0;
//        } else if (senderAccount.getTypeOfPlan().equals("silver")) {
//            if (ronAmount < 500) {
//                commission = 0;
//            } else {
//                commission = amount * 0.001;
//            }
//        } else if (senderAccount.getTypeOfPlan().equals("gold")) {
//            commission = 0;
//        }

//        for (final Commerciant checkCommerciant : Main.getCommerciants()) {
//            if (checkCommerciant.getName().equals(commerciant)) {
//                if(checkCommerciant.getCashbackStrategy().equals("spendingThreshold")) {
//                    cashback = SpendingThreshold.getCashback(amount,  senderAccount);
//                }
//                // add cashback for nrOfTransactions
//            }
//        }

        commission = Commission.calculateCommission(senderAccount, amount, senderAccount.getCurrency());

        if(senderAccount.getBalance() - amount - commission < 0) {
            senderAccount.addTransaction(new InsufficientFundsTransaction(timestamp, "Insufficient funds"));
            return;
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount - commission + cashback);
        //todo Cashback-ul se va efectua pentru tranzacția curentă la
        // orice comerciant ce are tipul de cashback spendingThreshold.

        final double ronAmount = Main.getCurrencyConverter().convert(amount, senderAccount.getCurrency(), "RON");
        senderAccount.setTotalAmountSpent(senderAccount.getTotalAmountSpent() + ronAmount);

        receiverAccount.setBalance(receiverAccount.getBalance() + convertedAmount);

        senderAccount.addTransaction(new SentTransaction(timestamp, description, senderIBAN,
                receiverIBAN, amount, senderAccount.getCurrency()));
        receiverAccount.addTransaction(new ReceivedTransaction(timestamp, description,
                senderIBAN, receiverIBAN, convertedAmount, receiverAccount.getCurrency()));

    }

    public void userNotFound(final ArrayNode output) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "sendMoney");
        commandOutput.put("timestamp", timestamp);
        final ObjectNode errorDetails = commandOutput.putObject("output");
        errorDetails.put("description", "User not found");
        errorDetails.put("timestamp", timestamp);
    }
}