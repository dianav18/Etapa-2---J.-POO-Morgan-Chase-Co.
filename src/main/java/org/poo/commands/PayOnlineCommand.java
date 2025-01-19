package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.*;
import org.poo.bankInput.transactions.*;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;
import org.poo.main.Main;
import org.poo.utils.Utils;

import java.util.List;

/**
 * It represents the pay online command, which is used to pay online with a card.
 */
public final class PayOnlineCommand implements CommandHandler {
    private final String cardNumber;
    private final double amount;
    private final String currency;
    private final int timestamp;
    private final String description;
    private final String commerciant;
    private final String email;
    private final List<User> users;
    private final CurrencyConverter currencyConverter;
    private double commission;
    private double cashback;

    /**
     * Instantiates a new Pay online command.
     *
     * @param cardNumber        the card number used for the payment
     * @param amount            the amount to be paid
     * @param currency          the currency of the payment
     * @param timestamp         the timestamp at which the payment is made
     * @param description       the description of the payment
     * @param commerciant       the commerciant receiving the payment
     * @param email             the email of the user
     * @param users             the users
     * @param currencyConverter the currency converter
     */
    public PayOnlineCommand(final String cardNumber, final double amount, final String currency,
                            final int timestamp, final String description,
                            final String commerciant, final String email,
                            final List<User> users, final CurrencyConverter currencyConverter) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.description = description;
        this.commerciant = commerciant;
        this.email = email;
        this.users = users;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public void execute(final ArrayNode output) {
        boolean cardFound = false;
        boolean userFound = false;

        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                userFound = true;
                break;
            }
        }

        if (!userFound) {
            return;
        }

        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                for (final Account account : user.getAccounts()) {
                    for (final Card card : account.getCards()) {
                        if (card.getCardNumber().equals(cardNumber)) {
                            if (card.getStatus().equals("frozen")) {
                                account.addTransaction(new CardFrozenTransaction(timestamp,
                                        "The card is frozen"));
                                return;
                            }

                            cardFound = true;
                            double finalAmount = amount;

                            if (!currency.equals(account.getCurrency())) {
                                finalAmount = currencyConverter.convert(amount, currency, account.getCurrency());
                            }

                            if (finalAmount <= 0) {
                                return;
                            }

                            commission = Commission.calculateCommission(account, finalAmount, account.getCurrency());

                            if (account.getBalance() < (finalAmount + commission)) {
                                account.addTransaction(new InsufficientFundsTransaction(timestamp, "Insufficient funds"));
                                return;
                            }

                            cashback = Cashback.calculateByCommerciantName(finalAmount, account.getCurrency(), account, commerciant);


                            System.out.println("Paying     :" + finalAmount);
                            System.out.println("Commission :" + commission);
                            System.out.println("Cashback   :" + cashback);

                            account.setBalance(account.getBalance() - finalAmount - commission + cashback);
                                boolean found = false;
                                for (final Commerciant userCommerciant : account.getCommerciants()) {
                                    if (userCommerciant.getName().equalsIgnoreCase(commerciant)) {
                                        userCommerciant.addSpentAmount(finalAmount);
                                        found = true;
                                        break;
                                    }
                                }

                            account.addCommerciantTransaction(new CommerciantTransaction(commerciant, finalAmount, timestamp));

                            if (!found) {
                                final Commerciant newCommerciant = new Commerciant.Builder(commerciant).build();
                                newCommerciant.addSpentAmount(finalAmount);
                                account.addCommerciant(newCommerciant);
                            }

                            account.addTransaction(new CardPaymentTransaction(
                                    timestamp, description,
                                    finalAmount, commerciant));

                            if (card.isOneTime()) {
                                account.addTransaction(new CardDestroyedTransaction(timestamp,
                                        "Card destroyed", account.getAccountIBAN(),
                                        card.getCardNumber(), user.getEmail()));
                                account.removeCard(card);
                                final Card newCard = new Card(Utils.generateCardNumber(), true);
                                account.addCard(newCard);
                                account.addTransaction(new CardCreatedTransaction(timestamp,
                                        account.getAccountIBAN(), newCard.getCardNumber(),
                                        user.getEmail()));
                            }
                        }
                    }
                }
                if (!cardFound) {
                    cardNotFoundOutput(output);
                    return;
                }
            }
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
