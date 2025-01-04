package org.poo.bankInput.transactions;

/**
 * The interface Transaction visitor.
 * This interface is used to implement the visitor pattern for transactions.
 */
public interface TransactionVisitor {
    /**
     * Visit.
     *
     * @param transaction the transaction to visit
     */
    void visit(AccountCreatedTransaction transaction);

    /**
     * Visit.
     *
     * @param transaction the transaction to visit
     */
    void visit(SentTransaction transaction);

    /**
     * Visit.
     *
     * @param transaction the transaction to visit
     */
    void visit(CardCreatedTransaction transaction);

    /**
     * Visit.
     *
     * @param cardPaymentTransaction the card payment transaction
     */
    void visit(CardPaymentTransaction cardPaymentTransaction);

    /**
     * Visit.
     *
     * @param transaction the transaction
     */
    void visit(InsufficientFundsTransaction transaction);

    /**
     * Visit.
     *
     * @param cardDestroyedTransaction the card destroyed transaction
     */
    void visit(CardDestroyedTransaction cardDestroyedTransaction);

    /**
     * Visit.
     *
     * @param cardFrozenTransaction the card frozen transaction
     */
    void visit(CardFrozenTransaction cardFrozenTransaction);

    /**
     * Visit.
     *
     * @param accountWaringTransaction the account waring transaction
     */
    void visit(AccountWarningTransaction accountWaringTransaction);

    /**
     * Visit.
     *
     * @param splitPaymentTransaction the split payment transaction
     */
    void visit(SplitPaymentTransaction splitPaymentTransaction);

    /**
     * Visit.
     *
     * @param commerciantTransaction the commerciant transaction
     */
    void visit(CommerciantTransaction commerciantTransaction);

    /**
     * Visit.
     *
     * @param receivedTransaction the received transaction
     */
    void visit(ReceivedTransaction receivedTransaction);

    /**
     * Visit.
     *
     * @param cannotDeleteAccountTransaction the cannot delete account transaction
     */
    void visit(CannotDeleteAccountTransaction cannotDeleteAccountTransaction);

    /**
     * Visit.
     *
     * @param changeInterestRateTransaction the change interest rate transaction
     */
    void visit(ChangeInterestRateTransaction changeInterestRateTransaction);

    void visit(WithdrawSavingsTransaction withdrawSavingsTransaction);

    void visit(UpgradePlanTransaction upgradePlanTransaction);
}
