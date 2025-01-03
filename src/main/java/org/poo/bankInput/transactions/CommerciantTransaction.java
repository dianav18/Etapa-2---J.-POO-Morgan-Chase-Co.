package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction made with a commerciant,
 * typically through a card payment.
 * This transaction includes details about the commerciant,
 * the transaction amount, and the timestamp.
 */
@Getter
public final class CommerciantTransaction extends Transaction {
    private final String commerciant;
    private final double amount;
    private final int timestamp;

    /**
     * Instantiates a new Commerciant transaction.
     *
     * @param commerciant the name of the commerciant involved in the transaction
     * @param amount      the amount paid in the transaction.
     * @param timestamp   the timestamp of the transaction at witch it occurred.
     */
    public CommerciantTransaction(final String commerciant,
                                  final double amount, final int timestamp) {
        super(timestamp, "Card payment", "commerciant");
        this.commerciant = commerciant;
        this.amount = amount;
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
