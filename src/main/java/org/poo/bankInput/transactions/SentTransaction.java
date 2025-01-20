package org.poo.bankInput.transactions;

import lombok.Getter;
import org.poo.bankInput.User;

/**
 * Represents a transaction that occurs when an
 * account sends money to another account.
 */
@Getter
public final class SentTransaction extends Transaction {

    private final User user;
    private final String senderIBAN;
    private final String receiverIBAN;
    private final double amount;
    private final String currency;

    /**
     * Instantiates a new Sent transaction.
     *
     * @param timestamp    the timestamp at which the transaction occurred.
     * @param description  a description of the transaction.
     * @param senderIBAN   the IBAN of the account sending the money.
     * @param receiverIBAN the IBAN of the account receiving the money.
     * @param amount       the amount of money sent.
     * @param currency     the currency of the transaction.
     */
    public SentTransaction(User user, final int timestamp, final String description,
                           final String senderIBAN, final String receiverIBAN,
                           final double amount, final String currency) {
        super(timestamp, description, "sent");
        this.user = user;
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
