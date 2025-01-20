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
     * @param commandName the command name
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

    /**
     * Allows duplication boolean.
     *
     * @return the boolean
     */
    public boolean allowsDuplication() {
        return false;
    }

    /**
     * Order int.
     *
     * @return the int
     */
    public int order() {
        return 0;
    }
}

