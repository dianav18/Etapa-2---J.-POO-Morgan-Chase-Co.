package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.BusinessAccount;
import org.poo.bankInput.SavingsAccount;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.AddInterestTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

import java.util.List;

@AllArgsConstructor
public class AddNewBusinessAssociateCommand implements CommandHandler {

    private final int timestamp;
    private final String account;
    private final String email;
    private final String role;


    @Override
    public void execute(final ArrayNode output) {
        Account account = Main.getAccount(this.account);
        User user = Main.getUser(email);

        if(account == null || !account.getType().equals("business") || user == null) {
            // TODO error
            return;
        }

        BusinessAccount businessAccount = (BusinessAccount) account;
        businessAccount.addUser(user, role);
    }
}