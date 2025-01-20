package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when a user upgrades their account plan.
 * This transaction indicates that a user has upgraded their account plan to a new type.
 */

@Getter
public class PlanAlreadyActiveTransaction extends Transaction{

    public PlanAlreadyActiveTransaction(final String plan, final int timestamp) {
        super(timestamp, "The user already has the " + plan + " plan.", "upgradePlan");
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
