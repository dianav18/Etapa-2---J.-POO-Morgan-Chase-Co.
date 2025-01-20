package org.poo.bankInput.transactions;

import lombok.Getter;
import org.poo.bankInput.User;

/**
 * Represents a transaction for a payment made using a card.
 * This transaction includes details about the amount paid, the commerciant involved,
 * and a description of the transaction.
 */
@Getter
public final class CardPaymentTransaction extends Transaction {

    private User user;
    private final double amount;
    private final String commerciant;
    private final String description;

    /**
     * Instantiates a new Card payment transaction.
     *
     * @param user        the user
     * @param timestamp   the timestamp of the transaction, indicating when the payment occurred.
     * @param description a description of the payment purpose.
     * @param amount      the amount of money involved in the transaction.
     * @param commerciant the name of the commerciant where the payment was made.
     */
    public CardPaymentTransaction(final User user, final int timestamp, final String description,
                                  final double amount, final String commerciant) {
        super(timestamp, description, "cardPayment");
        this.user = user;
        this.amount = amount;
        this.commerciant = commerciant;
        this.description = description;
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
