package org.poo.bankInput.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AddInterestTransaction extends Transaction {
    private final double amount;
    private final String currency;

    public AddInterestTransaction(final double amount, final String currency, final String description, final int timestamp) {
        super(timestamp, description, "addInterest");
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
