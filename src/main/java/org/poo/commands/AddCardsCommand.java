package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CardCreatedTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;
import org.poo.utils.Utils;

import java.util.List;

/**
 * Adds cards to an account of a user.
 */
public final class AddCardsCommand implements CommandHandler {
    private final String accountIBAN;
    private final String email;
    private final boolean isOneTime;
    private final int timestamp;
    private final List<User> users;

    /**
     * Instantiates a new Add cards command.
     *
     * @param accountIBAN the account iban
     * @param email       the email of the user
     * @param isOneTime   if the card is one time
     * @param timestamp   the timestamp at which the card is created
     * @param users       the users
     */
    public AddCardsCommand(final String accountIBAN, final String email,
                           final boolean isOneTime, final int timestamp, final List<User> users) {
        this.accountIBAN = accountIBAN;
        this.email = email;
        this.isOneTime = isOneTime;
        this.timestamp = timestamp;
        this.users = users;
    }

    @Override
    public void execute(final ArrayNode output) {
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

        Account account = Main.getAccount(accountIBAN);

        if (account == null) {
            return;
        }

        final String cardNumber = Utils.generateCardNumber();
        final Card newCard = new Card(account, cardNumber, isOneTime);
        account.addCard(newCard);
        account.addTransaction(new CardCreatedTransaction(timestamp, account.getAccountIBAN(), cardNumber, email));
    }
}
