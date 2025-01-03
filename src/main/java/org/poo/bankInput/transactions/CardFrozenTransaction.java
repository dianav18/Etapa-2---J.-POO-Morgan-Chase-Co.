package org.poo.bankInput.transactions;

/**
 * Represents a transaction that occurs when a card is frozen.
 * This transaction indicates that a specific card has been disabled temporarily
 * due to certain conditions, such as insufficient funds or security concerns.
 */
public final class CardFrozenTransaction extends Transaction {

    /**
     * Instantiates a new Card frozen transaction.
     *
     * @param timestamp   the timestamp at which the card was frozen.
     *                    This value represents the chronological order of the transaction.
     * @param description a description of the reason for freezing the card.
     */
    public CardFrozenTransaction(final int timestamp, final String description) {
        super(timestamp, description, "cardFrozen");
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
