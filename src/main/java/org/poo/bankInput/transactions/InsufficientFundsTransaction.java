package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when an account has insufficient funds
 * to complete a requested operation.
 */
@Getter
public final class InsufficientFundsTransaction extends Transaction {

    public InsufficientFundsTransaction(final int timestamp) {
        super(timestamp, "Insufficient funds", "insufficientFunds");
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
