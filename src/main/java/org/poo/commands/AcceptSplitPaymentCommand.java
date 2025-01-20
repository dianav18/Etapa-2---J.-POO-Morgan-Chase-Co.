package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.poo.Utils;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

/**
 * It represents the split payment command,
 * which is used to split a payment between multiple accounts.
 */
@AllArgsConstructor
public final class AcceptSplitPaymentCommand implements CommandHandler {

    private final int timestamp;
    private final String email;
    private String splitPaymentType;


    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final User user = Main.getUser(email);

        if (user == null) {
            Utils.userNotFound(output, "acceptSplitPayment");
            return;
        }

        for (final SplitPaymentCommand splitPayment : SplitPaymentCommand.ACTIVE_SPLIT_PAYMENTS) {
            if (splitPayment.getSplitPaymentType().equals(splitPaymentType)) {
                for (final Account account : user.getAccounts()) {
                    if (splitPayment.getAccountsForSplit().contains(account.getAccountIBAN())) {
                        splitPayment.accept(email, output);
                        return;
                    }
                }
            }
        }
    }
}
