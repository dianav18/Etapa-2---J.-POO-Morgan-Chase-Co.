package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction for withdrawing funds from a savings account.
 */
public final class WithdrawSavingsTransaction extends Transaction {
    /**
     * -- GETTER --
     *  Gets the withdrawn amount.
     *
     * @return the amount
     */
    @Getter
    private final double amount;
    private final String description;

    /**
     * Instantiates a new Withdraw savings transaction.
     *
     * @param timestamp   the timestamp of the transaction
     * @param description the description of the transaction
     * @param amount      the amount withdrawn
     */
    public WithdrawSavingsTransaction(final int timestamp, final String description, final double amount) {
        super(timestamp, description, "withdrawSavings");
        this.amount = amount;
        this.description = description;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
