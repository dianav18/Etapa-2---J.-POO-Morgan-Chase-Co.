package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.SplitPaymentTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CurrencyConverter;
import org.poo.main.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents the split payment command,
 * which is used to split a payment between multiple accounts.
 */
@AllArgsConstructor
public final class AcceptSplitPaymentCommand implements CommandHandler {

    private final int timestamp;
    private final String email;
    private String splitPaymentType;


    @Override
    public void execute(final ArrayNode output) {
        final User user = Main.getUser(email);

        if(user==null){
            // todo error
            return;
        }

        for (final SplitPaymentCommand splitPayment : SplitPaymentCommand. ACTIVE_SPLIT_PAYMENTS) {
            if(splitPayment.getSplitPaymentType().equals(splitPaymentType)){
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
