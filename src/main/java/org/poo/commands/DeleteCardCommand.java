package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CardDestroyedTransaction;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * It deletes a card from an account of a user.
 */
public final class DeleteCardCommand implements CommandHandler {
    private final String cardNumber;
    private final int timestamp;
    private final List<User> users;

    /**
     * Instantiates a new Delete card command.
     *
     * @param cardNumber the card number
     * @param timestamp  the timestamp
     * @param users      the users
     */
    public DeleteCardCommand(final String cardNumber, final int timestamp, final List<User> users) {
        this.cardNumber = cardNumber;
        this.timestamp = timestamp;
        this.users = users;
    }


    @Override
    public void execute(final ArrayNode output) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                for (final Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        account.addTransaction(new CardDestroyedTransaction(
                                timestamp,
                                "The card has been destroyed",
                                account.getAccountIBAN(),
                                card.getCardNumber(),
                                user.getEmail()
                        ));
                        card.destroy();
                        account.removeCard(card);
                        return;
                    }
                }
            }
        }
    }
}
