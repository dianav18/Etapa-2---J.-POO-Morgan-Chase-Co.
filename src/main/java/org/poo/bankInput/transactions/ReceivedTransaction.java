package org.poo.bankInput.transactions;

import lombok.Getter;

/**
 * Represents a transaction that occurs when an account receives money from another account.
 */
@Getter
public final class ReceivedTransaction extends Transaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String currency;

    /**
     * Instantiates a new Received transaction.
     *
     * @param timestamp    the timestamp of the transaction
     * @param description  details about the payment
     * @param senderIBAN   the sender iban
     * @param receiverIBAN the receiver iban
     * @param amount       the amount received
     * @param currency     the currency of the amount received
     */
    public ReceivedTransaction(final int timestamp, final String description,
                               final String senderIBAN, final String receiverIBAN,
                               final double amount, final String currency) {
        super(timestamp, description, "received");
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.currency = currency;
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
