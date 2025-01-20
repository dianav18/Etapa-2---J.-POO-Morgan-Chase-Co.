package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when a user upgrades their account plan.
 * This transaction indicates that a user has upgraded their account plan to a new type.
 */

@Getter
public class UpgradePlanTransaction extends Transaction{
    private final String accountIBAN;
    private final String newPlanType;

    public UpgradePlanTransaction(final String accountIBAN, final String description, final String newPlanType, final int timestamp) {
        super(timestamp, description, "upgradePlan");
        this.accountIBAN = accountIBAN;
        this.newPlanType = newPlanType;
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int order() {
        return 99;
    }
}
