package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when an account has insufficient funds
 * to complete a requested operation.
 */
@Getter
public final class InsufficientFundsTransaction extends Transaction {
    private final String description;
    private final int timestamp;

    /**
     * Instantiates a new Insufficient funds transaction.
     *
     * @param timestamp   the timestamp at which the command occurs
     * @param description the description of the insufficient funds transaction
     */
    public InsufficientFundsTransaction(final int timestamp, final String description) {
        super(timestamp, description, "insufficientFunds");
        this.description = description;
        this.timestamp = timestamp;
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
