package org.poo.bankInput.transactions;

import lombok.Getter;
import org.poo.commands.SplitPaymentCommand;

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
    private boolean rejected;

    /**
     * Instantiates a new Split payment transaction.
     *
     * @param command                the command
     * @param error                  the error
     * @param problematicAccountIBAN the problematic account iban
     * @param rejected               the rejected
     */
    public SplitPaymentTransaction(final SplitPaymentCommand command,
                                   final boolean error, final String problematicAccountIBAN,
                                   final boolean rejected) {
        super(command.getTimestamp(), "Split payment", "splitPayment");
        this.command = command;
        this.error = error;
        this.problematicAccountIBAN = problematicAccountIBAN;
        this.rejected = rejected;
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
