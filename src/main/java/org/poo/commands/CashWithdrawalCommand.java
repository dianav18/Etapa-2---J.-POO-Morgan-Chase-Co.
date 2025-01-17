package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

public class CashWithdrawalCommand implements CommandHandler {
    private final String command;
    private final String cardNumber;
    private final double amount;
    private final String email;
    private final String location;
    private final int timestamp;
    private final List<User> users;

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
                        }
                    }
                }
                return;
            }
        }
    }
}
