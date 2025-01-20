package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.poo.bankInput.Account;
import org.poo.bankInput.transactions.SplitPaymentTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents the split payment command,
 * which is used to split a payment between multiple accounts.
 */
@AllArgsConstructor
@Getter
public final class SplitPaymentCommand implements CommandHandler {

    /**
     * The constant ACTIVE_SPLIT_PAYMENTS.
     */
    public static final List<SplitPaymentCommand> ACTIVE_SPLIT_PAYMENTS = new ArrayList<>();

    private final int timestamp;
    private final String currency;
    private final double amount;
    private String splitPaymentType;

    private final List<String> accountsForSplit;
    private final List<Double> amountForUsers;
    private final List<String> acceptedAccounts = new ArrayList<>();

    /**
     * Accept.
     *
     * @param email  the email
     * @param output the output
     */
    public void accept(final String email, final ArrayNode output) {
        acceptedAccounts.add(email);

        if (acceptedAccounts.size() == accountsForSplit.size()) {
            finalizePayment(output);
        }
    }

    /**
     * Reject.
     *
     * @param email  the email
     * @param output the output
     */
    public void reject(final String email, final ArrayNode output) {
        for (final String accountIBAN : accountsForSplit) {
            final Account account = Main.getAccount(accountIBAN);

            if (account == null) {
                continue;
            }

            account.addTransaction(new SplitPaymentTransaction(this, false, null, true));
        }

        ACTIVE_SPLIT_PAYMENTS.remove(this);
    }

    /**
     * Gets part.
     *
     * @param iban the iban
     * @return the part
     */
    public double getPart(final String iban) {
        if (splitPaymentType.equals("equal")) {
            return amount / accountsForSplit.size();
        } else {
            return amountForUsers.get(accountsForSplit.indexOf(iban));
        }
    }

    /**
     * Finalize payment.
     *
     * @param output the output
     */
    public void finalizePayment(final ArrayNode output) {
        ACTIVE_SPLIT_PAYMENTS.remove(this);

        boolean hasError = false;
        String problematicAccountIBAN = null;

        for (final String accountIBAN : accountsForSplit) {
            final Account account = Main.getAccount(accountIBAN);

            if (account == null) {
                // todo error
                continue;
            }

            final double amountInAccountCurrency = Main.getCurrencyConverter()
                    .convert(getPart(accountIBAN), currency, account.getCurrency());

            if (account.getBalance() < amountInAccountCurrency) {
                problematicAccountIBAN = accountIBAN;
                hasError = true;
                break;
            }
        }

        if (hasError) {
            for (final String accountIBAN : accountsForSplit) {
                final Account account = Main.getAccount(accountIBAN);

                if (account == null) {
                    // todo error
                    continue;
                }

                account.addTransaction(new SplitPaymentTransaction(this, hasError,
                        problematicAccountIBAN, false));
            }
            return;
        }

        for (final String accountIBAN : accountsForSplit) {
            final Account account = Main.getAccount(accountIBAN);

            if (account == null) {
                // todo error
                continue;
            }

            final double amountInAccountCurrency = Main.getCurrencyConverter()
                    .convert(getPart(accountIBAN), currency, account.getCurrency());

            account.removeBalance(account.getOwner(), amountInAccountCurrency, 0, 0);
            account.addTransaction(new SplitPaymentTransaction(this, hasError,
                    problematicAccountIBAN, false));
        }
    }

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        SplitPaymentCommand.ACTIVE_SPLIT_PAYMENTS.add(this);
    }
}
