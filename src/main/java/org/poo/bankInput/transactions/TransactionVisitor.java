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

    /**
     * Visit.
     *
     * @param withdrawSavingsTransaction the withdraw savings transaction
     */
    void visit(WithdrawSavingsTransaction withdrawSavingsTransaction);

    /**
     * Visit.
     *
     * @param upgradePlanTransaction the upgrade plan transaction
     */
    void visit(UpgradePlanTransaction upgradePlanTransaction);

    /**
     * Visit.
     *
     * @param cashWithdrawalTransaction the cash withdrawal transaction
     */
    void visit(CashWithdrawalTransaction cashWithdrawalTransaction);

    /**
     * Visit.
     *
     * @param addInterestTransaction the add interest transaction
     */
    void visit(AddInterestTransaction addInterestTransaction);

    /**
     * Visit.
     *
     * @param noClassicAccountTransaction the no classic account transaction
     */
    void visit(NoClassicAccountTransaction noClassicAccountTransaction);

    /**
     * Visit.
     *
     * @param planAlreadyActiveTransaction the plan already active transaction
     */
    void visit(PlanAlreadyActiveTransaction planAlreadyActiveTransaction);

    /**
     * Visit.
     *
     * @param genericTransaction the generic transaction
     */
    void visit(GenericTransaction genericTransaction);
}
