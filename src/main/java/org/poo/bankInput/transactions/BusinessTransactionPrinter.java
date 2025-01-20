package org.poo.bankInput.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.BusinessAccount;
import org.poo.bankInput.Commerciant;
import org.poo.bankInput.User;
import org.poo.main.Main;

/**
 * The type Business transaction printer.
 */
public final class BusinessTransactionPrinter implements TransactionVisitor {
    private final ArrayNode commerciants;
    private BusinessAccount account;

    /**
     * Instantiates a new Business transaction printer.
     *
     * @param commerciants the commerciants
     * @param account      the account
     */
    public BusinessTransactionPrinter(final ArrayNode commerciants, final BusinessAccount account) {
        this.commerciants = commerciants;
        this.account = account;
    }

    private ObjectNode getCommerciantNode(final Commerciant commerciant) {
        for (final JsonNode jsonNode : commerciants) {
            if (jsonNode.get("commerciant").asText().equals(commerciant.getName())) {
                return (ObjectNode) jsonNode;
            }
        }

        final ObjectNode commerciantNode = commerciants.addObject();
        commerciantNode.put("commerciant", commerciant.getName());
        commerciantNode.putArray("employees");
        commerciantNode.putArray("managers");
        commerciantNode.put("total received", 0.0);

        return commerciantNode;
    }

    private void record(final User user, final double amount, final Commerciant commerciant) {
        final BusinessAccount.BusinessUser businessUser = account.getBusinessUser(user.getEmail());

        if (businessUser.getRole().equals("owner")) {
            return;
        }

        final ObjectNode commerciantNode = getCommerciantNode(commerciant);
        final ArrayNode array = (ArrayNode) commerciantNode.get(businessUser.getRole().equals(
                "employee") ? "employees" : "managers");
        array.add(user.getUsername());
        commerciantNode.put("total received", commerciantNode.get(
                "total received").asDouble() + amount);
    }

    /**
     * Visit.
     *
     * @param transaction the transaction
     */
    @Override
    public void visit(final AccountCreatedTransaction transaction) {
    }

    /**
     * Visit.
     *
     * @param transaction the transaction
     */
    @Override
    public void visit(final SentTransaction transaction) {
        final Commerciant commerciant = Main.getCommerciant(transaction.getReceiverIBAN());

        if (commerciant == null) {
            return;
        }

        record(transaction.getUser(), transaction.getAmount(), commerciant);
    }

    /**
     * Visit.
     *
     * @param transaction the transaction
     */
    @Override
    public void visit(final CardCreatedTransaction transaction) {
    }

    /**
     * Visit.
     *
     * @param transaction the transaction
     */
    @Override
    public void visit(final CardPaymentTransaction transaction) {
        final Commerciant commerciant = Main.getCommerciant(transaction.getCommerciant());

        if (commerciant == null) {
            return;
        }

        record(transaction.getUser(), transaction.getAmount(), commerciant);
    }

    /**
     * Visit.
     *
     * @param transaction the transaction
     */
    @Override
    public void visit(final InsufficientFundsTransaction transaction) {
    }

    /**
     * Visit.
     *
     * @param cardDestroyedTransaction the card destroyed transaction
     */
    @Override
    public void visit(final CardDestroyedTransaction cardDestroyedTransaction) {
    }

    /**
     * Visit.
     *
     * @param cardFrozenTransaction the card frozen transaction
     */
    @Override
    public void visit(final CardFrozenTransaction cardFrozenTransaction) {
    }

    /**
     * Visit.
     *
     * @param accountWaringTransaction the account waring transaction
     */
    @Override
    public void visit(final AccountWarningTransaction accountWaringTransaction) {
    }

    /**
     * Visit.
     *
     * @param splitPaymentTransaction the split payment transaction
     */
    @Override
    public void visit(final SplitPaymentTransaction splitPaymentTransaction) {
    }

    /**
     * Visit.
     *
     * @param transaction the transaction
     */
    @Override
    public void visit(final CommerciantTransaction transaction) {
        final Commerciant commerciant = Main.getCommerciant(transaction.getCommerciant());

        if (commerciant == null) {
            return;
        }

        record(transaction.getUser(), transaction.getAmount(), commerciant);
    }

    /**
     * Visit.
     *
     * @param receivedTransaction the received transaction
     */
    @Override
    public void visit(final ReceivedTransaction receivedTransaction) {
    }

    /**
     * Visit.
     *
     * @param cannotDeleteAccountTransaction the cannot delete account transaction
     */
    @Override
    public void visit(final CannotDeleteAccountTransaction cannotDeleteAccountTransaction) {
    }

    /**
     * Visit.
     *
     * @param changeInterestRateTransaction the change interest rate transaction
     */
    @Override
    public void visit(final ChangeInterestRateTransaction changeInterestRateTransaction) {
    }

    /**
     * Visit.
     *
     * @param withdrawSavingsTransaction the withdraw savings transaction
     */
    @Override
    public void visit(final WithdrawSavingsTransaction withdrawSavingsTransaction) {
    }

    /**
     * Visit.
     *
     * @param upgradePlanTransaction the upgrade plan transaction
     */
    @Override
    public void visit(final UpgradePlanTransaction upgradePlanTransaction) {
    }

    /**
     * Visit.
     *
     * @param cashWithdrawalTransaction the cash withdrawal transaction
     */
    @Override
    public void visit(final CashWithdrawalTransaction cashWithdrawalTransaction) {
    }

    /**
     * Visit.
     *
     * @param addInterestTransaction the add interest transaction
     */
    @Override
    public void visit(final AddInterestTransaction addInterestTransaction) {
    }

    /**
     * Visit.
     *
     * @param noClassicAccountTransaction the no classic account transaction
     */
    @Override
    public void visit(final NoClassicAccountTransaction noClassicAccountTransaction) {
    }

    /**
     * Visit.
     *
     * @param planAlreadyActiveTransaction the plan already active transaction
     */
    @Override
    public void visit(final PlanAlreadyActiveTransaction planAlreadyActiveTransaction) {
    }

    /**
     * Visit.
     *
     * @param genericTransaction the generic transaction
     */
    @Override
    public void visit(final GenericTransaction genericTransaction) {
    }
}
