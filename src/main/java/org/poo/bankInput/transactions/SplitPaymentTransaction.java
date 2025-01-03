package org.poo.bankInput.transactions;

import lombok.Getter;

import java.util.List;

/**
 * This transaction is used to process a split payment.
 * This transaction includes details about the currency,
 * the accounts involved, the amount per account, and the total amount.
 * If an error occurs during the transaction, the transaction will include
 * details about the problematic account.
 */
@Getter
public final class SplitPaymentTransaction extends Transaction {
    private final String currency;
    private final List<String> involvedAccounts;
    private final double amountPerAccount;
    private final double totalAmount;

    private final boolean showError;
    private final String problematicAccountIBAN;

    /**
     * Instantiates a new Split payment transaction.
     *
     * @param timestamp              the timestamp of the transaction
     * @param currency               the currency of the transaction
     * @param involvedAccounts       the involved accounts in the transaction
     * @param amount                 the amount per account
     * @param totalAmount            the total amount of the transaction
     * @param showError              the show error flag
     * @param problematicAccountIBAN the problematic account iban in case of error
     */
    public SplitPaymentTransaction(final int timestamp, final String currency,
                                   final List<String> involvedAccounts,
                                   final double amount, final double totalAmount,
                                   final boolean showError,
                                   final String problematicAccountIBAN) {
        super(timestamp, "Split payment", "splitPayment");
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.amountPerAccount = amount;
        this.totalAmount = totalAmount;
        this.showError = showError;
        this.problematicAccountIBAN = problematicAccountIBAN;
    }

    /**
     * Accepts a {@link TransactionVisitor} to process this transaction type.
     *
     * @param visitor the visitor object that processes the transaction.
     */
    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
