package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * The type Cash withdrawal transaction.
 */
@Getter
public class CashWithdrawalTransaction extends Transaction {
    private final double amount;

    /**
     * Instantiates a new Cash withdrawal transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     * @param amount      the amount
     */
    public CashWithdrawalTransaction(final int timestamp, final String description,
                                     final double amount) {
        super(timestamp, description, "cashWithdrawal");
        this.amount = amount;
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
