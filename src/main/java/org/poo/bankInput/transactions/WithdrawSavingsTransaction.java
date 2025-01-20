package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction for withdrawing funds from a savings account.
 */
@Getter
public final class WithdrawSavingsTransaction extends Transaction {
    /**
     * -- GETTER --
     * Gets the withdrawn amount.
     *
     * @return the amount
     */
    @Getter
    private final double amount;
    private final String classicAccountIBAN;
    private final String savingsAccountIBAN;


    /**
     * Instantiates a new Withdraw savings transaction.
     *
     * @param timestamp          the timestamp
     * @param amount             the amount
     * @param classicAccountIBAN the classic account iban
     * @param savingsAccountIBAN the savings account iban
     */
    public WithdrawSavingsTransaction(final int timestamp, final double amount,
                                      final String classicAccountIBAN,
                                      final String savingsAccountIBAN) {
        super(timestamp, "Savings withdrawal", "withdrawSavings");
        this.amount = amount;
        this.classicAccountIBAN = classicAccountIBAN;
        this.savingsAccountIBAN = savingsAccountIBAN;
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

    /**
     * Allows duplication boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean allowsDuplication() {
        return true;
    }
}
