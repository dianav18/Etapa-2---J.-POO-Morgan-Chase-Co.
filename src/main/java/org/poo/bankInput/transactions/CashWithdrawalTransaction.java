package org.poo.bankInput.transactions;

import lombok.Getter;

@Getter
public class CashWithdrawalTransaction extends Transaction{
    private final double amount;

    public CashWithdrawalTransaction(final int timestamp, final String description, final double amount) {
        super(timestamp, description, "cashWithdrawal");
        this.amount = amount;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
