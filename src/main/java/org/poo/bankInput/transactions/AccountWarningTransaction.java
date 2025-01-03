package org.poo.bankInput.transactions;

/**
 * Represents a transaction that occurs when an account reaches
 * a warning state, such as nearing the minimum balance.
 * This transaction notifies the user about potential issues
 * with their account balance.
 */
public final class AccountWarningTransaction extends Transaction {

    /**
     * Creates a new instance of an account warning transaction.
     *
     * @param timestamp   the timestamp at which the warning occurred.
     *                    This value represents the chronological order of the transaction.
     * @param description a detailed description of the warning.
     *                    For example, it might indicate that the account balance
     *                    is close to the minimum threshold.
     */
    public AccountWarningTransaction(final int timestamp, final String description) {
        super(timestamp, description, "accountWarning");
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
