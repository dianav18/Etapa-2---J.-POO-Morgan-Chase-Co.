package org.poo.bankInput.transactions;

public class NoClassicAccountTransaction extends Transaction {


    public NoClassicAccountTransaction(final int timestamp) {
        super(timestamp, "You do not have a classic account.", "withdrawSavings");
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