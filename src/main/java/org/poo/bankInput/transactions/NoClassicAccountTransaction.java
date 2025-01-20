package org.poo.bankInput.transactions;

/**
 * The type No classic account transaction.
 */
public class NoClassicAccountTransaction extends Transaction {


    /**
     * Instantiates a new No classic account transaction.
     *
     * @param timestamp the timestamp
     */
    public NoClassicAccountTransaction(final int timestamp) {
        super(timestamp, "You do not have a classic account.",
                "withdrawSavings");
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
