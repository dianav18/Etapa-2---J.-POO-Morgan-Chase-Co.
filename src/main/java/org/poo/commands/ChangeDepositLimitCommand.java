package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.BusinessAccount;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

/**
 * The type Change deposit limit command.
 */
/*
        },
        {
            "command": "changeSpendingLimit",
            "email": "Pauline_Roux_du_Breton@hotmail.fr",
            "account": "RO58POOB2721178218599590",
            "amount": 339,
            "timestamp": 86
        },
 */
@AllArgsConstructor
public class ChangeDepositLimitCommand implements CommandHandler {

    private String email;
    private String account;
    private double amount;
    private long timestamp;

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final User user = Main.getUser(email);
        final Account localAccount = Main.getAccount(this.account);

        if (user == null || localAccount == null || !localAccount.getType().equals("business")) {
            return;
        }

        final BusinessAccount businessAccount = (BusinessAccount) localAccount;
        if (!businessAccount.setDepositLimit(user, amount)) {
            final ObjectNode node = output.addObject();
            node.put("command", "changeDepositLimit");
            node.put("timestamp", timestamp);
            final ObjectNode outputNode = node.putObject("output");
            outputNode.put("description", "You must be owner in order to change deposit limit.");
            outputNode.put("timestamp", timestamp);
        }
    }
}
