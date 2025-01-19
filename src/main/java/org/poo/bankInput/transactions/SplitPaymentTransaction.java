package org.poo.bankInput.transactions;

import lombok.Getter;
import org.poo.commands.SplitPaymentCommand;

import java.util.List;

/**
 * This transaction is used to process a split payment.
 * This transaction includes details about the currency,
 * the accounts involved, the amount per account, and the total amount.
 * If an error occurs during the transaction, the transaction will include
 * details about the problematic account.
 */
@Getter
public final class SplitPaymentTransaction extends Transaction {
    private final SplitPaymentCommand command;
    private final boolean error;
    private final String problematicAccountIBAN;

    public SplitPaymentTransaction(final SplitPaymentCommand command, final boolean error, final String problematicAccountIBAN) {
        super(command.getTimestamp(), "Split payment", "splitPayment");
        this.command = command;
        this.error = error;
        this.problematicAccountIBAN = problematicAccountIBAN;
    }

    /**
     * Accepts a {@link TransactionVisitor} to process this transaction type.
     *
     * @param visitor the visitor object that processes the transaction.
     */
    @Override
    public void accept(final TransactionVisitor visitor) {
        visitor.visit(this);
    }
}
