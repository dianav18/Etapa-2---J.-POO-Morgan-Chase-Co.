package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CardDestroyedTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

/**
 * It deletes a card from an account of a user.
 */
@AllArgsConstructor
public final class DeleteCardCommand implements CommandHandler {
    private final String cardNumber;
    private final int timestamp;


    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        for (final User user : Main.getUsers()) {
            for (final Account account : user.getAccounts()) {
                if (account.getBalance() != 0) {
                    return;
                }

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
