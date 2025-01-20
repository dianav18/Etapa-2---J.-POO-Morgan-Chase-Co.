package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.poo.Utils;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.CashWithdrawalTransaction;
import org.poo.bankInput.transactions.InsufficientFundsTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

@AllArgsConstructor
public class CashWithdrawalCommand implements CommandHandler {

    private final String cardNumber;
    private final double amount;
    private final String email;
    private final int timestamp;

    @Override
    public void execute(final ArrayNode output) {
        User user = Main.getUser(email);

        if (user == null) {
            Utils.userNotFound(output, "cashWithdrawal");
            return;
        }

        Card card = Main.getCard(user, cardNumber);

        if (card == null) {
            Utils.cardNotFound(output, "cashWithdrawal");
            return;
        }

        Account account = card.getAccount();

        if (!account.removeBalance(user, amount, "RON", null)) {
            account.addTransaction(new InsufficientFundsTransaction(timestamp));
            return;
        }

        account.addTransaction(new CashWithdrawalTransaction(timestamp, "Cash withdrawal of " + amount, amount));
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
