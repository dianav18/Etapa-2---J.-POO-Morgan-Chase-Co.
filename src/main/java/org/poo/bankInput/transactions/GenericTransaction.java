package org.poo.bankInput.transactions;

import lombok.Getter;
import org.poo.main.Main;

/**
 * Represents a transaction that occurs when a user upgrades their account plan.
 * This transaction indicates that a user has upgraded their account plan to a new type.
 */
@Getter
public class GenericTransaction extends Transaction {

    /**
     * Instantiates a new Generic transaction.
     *
     * @param description the description
     */
    public GenericTransaction(final String description) {
        super(Main.getTimestamp(), description, "generic");
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
