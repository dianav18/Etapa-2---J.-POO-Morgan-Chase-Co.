package org.poo.bankInput.transactions;

/**
 * Represents a transaction that occurs when a new account is created.
 * This transaction is automatically added to the transaction history
 * when an account is successfully created.
 */
public final class AccountCreatedTransaction extends Transaction {
    /**
     * Creates a new instance of an account creation transaction.
     *
     * @param timestamp the timestamp at which the account was created.
     *                  This value represents the chronological order of the transaction.
     */
    public AccountCreatedTransaction(final int timestamp) {
        super(timestamp, "New account created", "accountCreated");
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
