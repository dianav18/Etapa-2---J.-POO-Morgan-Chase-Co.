package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when a user upgrades their account plan.
 * This transaction indicates that a user has upgraded their account plan to a new type.
 */
@Getter
public class UpgradePlanTransaction extends Transaction {
    private final String accountIBAN;
    private final String newPlanType;

    /**
     * Instantiates a new Upgrade plan transaction.
     *
     * @param accountIBAN the account iban
     * @param description the description
     * @param newPlanType the new plan type
     * @param timestamp   the timestamp
     */
    public UpgradePlanTransaction(final String accountIBAN, final String description,
                                  final String newPlanType, final int timestamp) {
        super(timestamp, description, "upgradePlan");
        this.accountIBAN = accountIBAN;
        this.newPlanType = newPlanType;
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

    private static final int BIG_NUMBER = 100;

    /**
     * Order int.
     *
     * @return the int
     */
    @Override
    public int order() {
        return BIG_NUMBER;
    }
}
