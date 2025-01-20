package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.Commerciant;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.*;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;
import org.poo.utils.Utils;

/**
 * It represents the pay online command, which is used to pay online with a card.
 */
@AllArgsConstructor
public final class PayOnlineCommand implements CommandHandler {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final int timestamp;
    private final String description;
    private final String commerciant;
    private final String email;


    @Override
    public void execute(final ArrayNode output) {
        if (amount == 0) {
            return;
        }

        User user = Main.getUser(email);

        if (user == null) {
            cardNotFoundOutput(output);
            return;
        }

        Card card = Main.getCard(user, cardNumber);

        if (card == null) {
            cardNotFoundOutput(output);
            return;
        }

        Account account = card.getAccount();

        if (card.getStatus().equals("frozen")) {
            account.addTransaction(new CardFrozenTransaction(timestamp, "The card is frozen"));
            return;
        }

        if (!account.removeBalance(user, amount, currency, commerciant)) {
            account.addTransaction(new InsufficientFundsTransaction(timestamp));
            return;
        }

        double convertedAmount = Main.getCurrencyConverter().convert(amount, currency, account.getCurrency());

        boolean found = false;
        for (final Commerciant userCommerciant : account.getCommerciants()) {
            if (userCommerciant.getName().equalsIgnoreCase(commerciant)) {
                userCommerciant.addSpentAmount(convertedAmount);
                found = true;
                break;
            }
        }

        account.addCommerciantTransaction(new CommerciantTransaction(user, commerciant, convertedAmount, timestamp));

        if (!found) {
            final Commerciant newCommerciant = new Commerciant.Builder(commerciant).build();
            newCommerciant.addSpentAmount(convertedAmount);
            account.addCommerciant(newCommerciant);
        }

        account.addTransaction(new CardPaymentTransaction(user, timestamp, description, convertedAmount, commerciant));

        if (card.isOneTime()) {
            account.addTransaction(new CardDestroyedTransaction(timestamp, "Card destroyed", account.getAccountIBAN(), card.getCardNumber(), user.getEmail()));
            account.removeCard(card);
            final Card newCard = new Card(account, Utils.generateCardNumber(), true);
            account.addCard(newCard);
            account.addTransaction(new CardCreatedTransaction(timestamp, account.getAccountIBAN(), newCard.getCardNumber(), user.getEmail()));
        }


    }

    private void cardNotFoundOutput(final ArrayNode output) {
        final ObjectNode commandOutput = output.addObject();
        commandOutput.put("command", "payOnline");
        commandOutput.put("timestamp", timestamp);
        final ObjectNode errorDetails = commandOutput.putObject("output");
        errorDetails.put("description", "Card not found");
        errorDetails.put("timestamp", timestamp);
    }
}
