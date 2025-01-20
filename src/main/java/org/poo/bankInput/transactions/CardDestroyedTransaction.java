package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when a card is destroyed.
 * This transaction records details about the associated account,
 * the card, and the email of the cardholder.
 */
@Getter
public final class CardDestroyedTransaction extends Transaction {
    private final String account;
    private final String card;
    private final String email;

    /**
     * Creates a new instance of a card destruction transaction.
     *
     * @param timestamp   the timestamp at which the card was destroyed
     *                    This value represents the chronological order of the transaction.
     * @param description a description of the transaction.
     * @param account     the IBAN of the account associated with the destroyed card.
     * @param card        the unique card number for the destroyed card.
     * @param email       the email address of the user who owned the card.
     */
    public CardDestroyedTransaction(final int timestamp, final String description,
                                    final String account, final String card, final String email) {
        super(timestamp, description, "cardDestroyed");
        this.account = account;
        this.card = card;
        this.email = email;
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
