package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when a user upgrades their account plan.
 * This transaction indicates that a user has upgraded their account plan to a new type.
 */
@Getter
public class PlanAlreadyActiveTransaction extends Transaction {

    /**
     * Instantiates a new Plan already active transaction.
     *
     * @param plan      the plan
     * @param timestamp the timestamp
     */
    public PlanAlreadyActiveTransaction(final String plan, final int timestamp) {
        super(timestamp, "The user already has the " + plan + " plan.",
                "upgradePlan");
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
