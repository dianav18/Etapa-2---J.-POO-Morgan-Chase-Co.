package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * The type Add interest transaction.
 */
@Getter
public class AddInterestTransaction extends Transaction {
    private final double amount;
    private final String currency;

    /**
     * Instantiates a new Add interest transaction.
     *
     * @param amount      the amount
     * @param currency    the currency
     * @param description the description
     * @param timestamp   the timestamp
     */
    public AddInterestTransaction(final double amount, final String currency,
                                  final String description, final int timestamp) {
        super(timestamp, description, "addInterest");
        this.amount = amount;
        this.currency = currency;
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
