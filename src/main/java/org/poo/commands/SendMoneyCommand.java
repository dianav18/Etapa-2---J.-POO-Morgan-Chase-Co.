package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.Utils;
import org.poo.bankInput.Account;
import org.poo.bankInput.Commerciant;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.InsufficientFundsTransaction;
import org.poo.bankInput.transactions.ReceivedTransaction;
import org.poo.bankInput.transactions.SentTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

/**
 * It represents the send money command, which is used to send money from one account to another.
 */
@AllArgsConstructor
public final class SendMoneyCommand implements CommandHandler {

    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final int timestamp;
    private final String description;

    @Override
    public void execute(final ArrayNode output) {
        Account senderAccount = Main.getAccount(senderIBAN);
        Account receiverAccount = Main.getAccount(receiverIBAN);

        if (senderAccount == null) {
            Utils.userNotFound(output, "sendMoney");
            return;
        }

        if (receiverAccount == null && Main.getCommerciant(receiverIBAN) == null) {
            Utils.userNotFound(output, "sendMoney");
            return;
        }

        User senderUser = senderAccount.getOwner();

        if (!senderAccount.removeBalance(senderUser, amount, senderAccount.getCurrency(), receiverIBAN)) {
            senderAccount.addTransaction(new InsufficientFundsTransaction(timestamp));
            return;
        }

        if (receiverAccount != null) {
            receiverAccount.addBalance(receiverAccount.getOwner(), amount, senderAccount.getCurrency());
            double convertedAmount = Main.getCurrencyConverter().convert(amount, senderAccount.getCurrency(), receiverAccount.getCurrency());
            receiverAccount.addTransaction(new ReceivedTransaction(timestamp, description, senderIBAN, receiverIBAN, convertedAmount, receiverAccount.getCurrency()));
        }

        senderAccount.addTransaction(new SentTransaction(senderUser, timestamp, description, senderIBAN, receiverIBAN, amount, senderAccount.getCurrency()));

    }

}