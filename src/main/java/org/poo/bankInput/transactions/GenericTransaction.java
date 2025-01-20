package org.poo.bankInput.transactions;

import lombok.Getter;
import org.poo.main.Main;

/**
 * Represents a transaction that occurs when a user upgrades their account plan.
 * This transaction indicates that a user has upgraded their account plan to a new type.
 */

@Getter
public class GenericTransaction extends Transaction{

    public GenericTransaction(final String description) {
        super(Main.getTimestamp(), description, "generic");
    }

    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
