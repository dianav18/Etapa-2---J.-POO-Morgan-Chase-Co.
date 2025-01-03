package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs in the bank.
 */
@Getter
public abstract class Transaction {
    private final int timestamp;
    private final String description;
    private final String commandName;

    /**
     * Instantiates a new Transaction.
     *
     * @param timestamp   the timestamp of the transaction
     * @param description the description of the transaction
     */
    public Transaction(final int timestamp, final String description, final String commandName) {
        this.timestamp = timestamp;
        this.description = description;
        this.commandName = commandName;
    }

    /**
     * Accept.
     *
     * @param visitor the visitor
     */
    public abstract void accept(TransactionVisitor visitor);
}

