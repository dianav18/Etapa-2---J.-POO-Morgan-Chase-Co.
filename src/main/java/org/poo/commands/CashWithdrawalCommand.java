package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.*;
import org.poo.bankInput.transactions.CashWithdrawalTransaction;
import org.poo.bankInput.transactions.InsufficientFundsTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;
import org.poo.utils.Utils;

import java.util.List;

public class CashWithdrawalCommand implements CommandHandler {
    private final String command;
    private final String cardNumber;
    private final double amount;
    private final String email;
    private final String location;
    private final int timestamp;
    private final List<User> users;
    private double commission;

    public CashWithdrawalCommand(final String command, final String cardNumber, final double amount, final String email, final String location, final int timestamp, final List<User> users) {
        this.command = command;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.email = email;
        this.location = location;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    for (final Card card : account.getCards()) {
                        if (card.getCardNumber().equals(cardNumber)) {
                            if (account.getBalance() < amount) {
                                account.addTransaction(new InsufficientFundsTransaction(timestamp, "Insufficient funds"));
                                return;
                            }

                            final double convertedAmount = Main.getCurrencyConverter().convert(amount, "RON", account.getCurrency());
                            commission = Commission.calculateCommission(account, convertedAmount, account.getCurrency());
                            account.setBalance(account.getBalance() - convertedAmount - commission);
                            account.addTransaction(new CashWithdrawalTransaction(timestamp, "Cash withdrawal of " + amount, amount));

//                            if (card.isOneTime()) {
//                                account.removeCard(card);
//                                final Card newCard = new Card(Utils.generateCardNumber(), true);
//                                account.addCard(newCard);
//                            }
                            return;
                        }
                    }
                }
            }
        }
        cardNotFoundOutput(output);
    }

    private void cardNotFoundOutput(final ArrayNode output) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "cashWithdrawal");
        commandOutput.put("timestamp", timestamp);
        final ObjectNode errorDetails = commandOutput.putObject("output");
        errorDetails.put("description", "Card not found");
        errorDetails.put("timestamp", timestamp);
    }
}
