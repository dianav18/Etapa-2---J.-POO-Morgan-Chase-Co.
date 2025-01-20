package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that changes the interest rate of an account.
 * This transaction includes details about the new interest rate and a description of the change.
 */
@Getter
public final class ChangeInterestRateTransaction extends Transaction {
    private final double interestRate;

    /**
     * Instantiates a new Change interest rate transaction.
     *
     * @param timestamp    the timestamp of the transaction
     *                     indicating when the interest rate change occurred.
     * @param interestRate the new interest rate applied to the account.
     * @param description  a description of the interest rate change.
     */
    public ChangeInterestRateTransaction(final int timestamp, final double interestRate,
                                         final String description) {
        super(timestamp, description, "changeInterestRate");
        this.interestRate = interestRate;
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
