package org.poo.bankInput.transactions;

/**
 * The type Cannot delete account transaction.
 */
public final class CannotDeleteAccountTransaction extends Transaction {
    /**
     * Instantiates a new Cannot delete account transaction.
     *
     * @param timestamp   the timestamp at which the account is deleted
     *                    This value represents the chronological order of the transaction.
     * @param description a detailed description for the operation.
     */
    public CannotDeleteAccountTransaction(final int timestamp, final String description) {
        super(timestamp, description, "cannotDeleteAccount");
    }

    /**
     * Accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
