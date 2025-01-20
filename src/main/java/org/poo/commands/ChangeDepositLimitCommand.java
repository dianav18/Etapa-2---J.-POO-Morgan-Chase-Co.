package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.BusinessAccount;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

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

    @Override
    public void execute(ArrayNode output) {
        User user  = Main.getUser(email);
        Account account = Main.getAccount(this.account);

        if (user == null || account == null || !account.getType().equals("business")) {
            return;
        }

        BusinessAccount businessAccount = (BusinessAccount) account;
        if(!businessAccount.setDepositLimit(user, amount)){
            ObjectNode node =  output.addObject();
            node.put("command", "changeDepositLimit");
            node.put("timestamp", timestamp);
            ObjectNode outputNode = node.putObject("output");
            outputNode.put("description", "You must be owner in order to change deposit limit.");
            outputNode.put("timestamp", timestamp);
        }
    }
}
