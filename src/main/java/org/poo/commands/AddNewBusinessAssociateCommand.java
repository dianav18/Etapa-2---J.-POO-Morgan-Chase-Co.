package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.BusinessAccount;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

/**
 * The type Add new business associate command.
 */
@AllArgsConstructor
public class AddNewBusinessAssociateCommand implements CommandHandler {

    private final int timestamp;
    private final String account;
    private final String email;
    private final String role;


    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final Account localAccount = Main.getAccount(this.account);
        final User user = Main.getUser(email);

        if (localAccount == null || !localAccount.getType().equals("business") || user == null) {
            return;
        }

        final BusinessAccount businessAccount = (BusinessAccount) localAccount;
        businessAccount.addUser(user, role);
    }
}
