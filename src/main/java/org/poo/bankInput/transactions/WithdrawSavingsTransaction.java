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


    public WithdrawSavingsTransaction(final int timestamp, final double amount, String classicAccountIBAN, String savingsAccountIBAN) {
        super(timestamp, "Savings withdrawal", "withdrawSavings");
        this.amount = amount;
        this.classicAccountIBAN = classicAccountIBAN;
        this.savingsAccountIBAN = savingsAccountIBAN;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean allowsDuplication() {
        return true;
    }
}
