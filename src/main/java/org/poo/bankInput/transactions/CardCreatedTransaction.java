package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when a new card is created
 * and associated with an account. This transaction records details
 * about the associated account, card number, and cardholder.
 */
@Getter
public final class CardCreatedTransaction extends Transaction {
    /**
     * The account associated with the created card.
     */
    private final String account;

    /**
     * The unique identifier of the created card.
     */
    private final String card;

    /**
     * The email address of the cardholder who owns the created card.
     */
    private final String cardHolder;

    /**
     * Creates a new instance of a card creation transaction.
     *
     * @param timestamp  the timestamp at which the card was created.
     *                   This value represents the chronological order of the transaction.
     * @param account    the IBAN of the account associated with the created card.
     * @param card       the unique card number for the created card.
     * @param cardHolder the email address of the user who owns the card.
     */
    public CardCreatedTransaction(final int timestamp, final String account,
                                  final String card, final String cardHolder) {
        super(timestamp, "New card created", "cardCreated");
        this.account = account;
        this.card = card;
        this.cardHolder = cardHolder;
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
