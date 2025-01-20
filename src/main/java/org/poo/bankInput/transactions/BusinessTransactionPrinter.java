package org.poo.bankInput.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.BusinessAccount;
import org.poo.bankInput.Commerciant;
import org.poo.bankInput.User;
import org.poo.main.Main;

public final class BusinessTransactionPrinter implements TransactionVisitor {
    private final ArrayNode commerciants;
    private BusinessAccount account;

    public BusinessTransactionPrinter(ArrayNode commerciants, BusinessAccount account) {
        this.commerciants = commerciants;
        this.account = account;
    }

    private ObjectNode getCommerciantNode(Commerciant commerciant) {
        for (JsonNode jsonNode : commerciants) {
            if (jsonNode.get("commerciant").asText().equals(commerciant.getName())) {
                return (ObjectNode) jsonNode;
            }
        }

        ObjectNode commerciantNode = commerciants.addObject();
        commerciantNode.put("commerciant", commerciant.getName());
        commerciantNode.putArray("employees");
        commerciantNode.putArray("managers");
        commerciantNode.put("total received", 0.0);

        return commerciantNode;
    }

    private void record(User user, double amount, Commerciant commerciant){
        BusinessAccount.BusinessUser businessUser = account.getBusinessUser(user.getEmail());

        if (businessUser.getRole().equals("owner")) {
            return;
        }

        ObjectNode commerciantNode = getCommerciantNode(commerciant);
        ArrayNode array = (ArrayNode) commerciantNode.get(businessUser.getRole().equals("employee") ? "employees" : "managers");
        array.add(user.getUsername());
        commerciantNode.put("total received", commerciantNode.get("total received").asDouble() + amount);
    }

    @Override
    public void visit(final AccountCreatedTransaction transaction) {
    }

    @Override
    public void visit(final SentTransaction transaction) {
        Commerciant commerciant = Main.getCommerciant(transaction.getReceiverIBAN());

        if (commerciant == null) {
            return;
        }

        record(transaction.getUser(), transaction.getAmount(), commerciant);
    }

    @Override
    public void visit(final CardCreatedTransaction transaction) {
    }

    @Override
    public void visit(final CardPaymentTransaction transaction) {
        Commerciant commerciant = Main.getCommerciant(transaction.getCommerciant());

        if (commerciant == null) {
            return;
        }

        record(transaction.getUser(), transaction.getAmount(), commerciant);
    }

    @Override
    public void visit(final InsufficientFundsTransaction transaction) {
    }

    @Override
    public void visit(final CardDestroyedTransaction cardDestroyedTransaction) {
    }

    @Override
    public void visit(final CardFrozenTransaction cardFrozenTransaction) {
    }

    @Override
    public void visit(final AccountWarningTransaction accountWaringTransaction) {
    }

    @Override
    public void visit(final SplitPaymentTransaction splitPaymentTransaction) {
    }

    @Override
    public void visit(final CommerciantTransaction transaction) {
        Commerciant commerciant = Main.getCommerciant(transaction.getCommerciant());

        if (commerciant == null) {
            return;
        }

        record(transaction.getUser(), transaction.getAmount(), commerciant);
    }

    @Override
    public void visit(final ReceivedTransaction receivedTransaction) {
    }

    @Override
    public void visit(final CannotDeleteAccountTransaction cannotDeleteAccountTransaction) {
    }

    @Override
    public void visit(final ChangeInterestRateTransaction changeInterestRateTransaction) {
    }

    @Override
    public void visit(final WithdrawSavingsTransaction withdrawSavingsTransaction) {
    }

    @Override
    public void visit(final UpgradePlanTransaction upgradePlanTransaction) {
    }

    @Override
    public void visit(final CashWithdrawalTransaction cashWithdrawalTransaction) {
    }

    @Override
    public void visit(final AddInterestTransaction addInterestTransaction) {
    }

    @Override
    public void visit(NoClassicAccountTransaction noClassicAccountTransaction) {
    }

    @Override
    public void visit(PlanAlreadyActiveTransaction planAlreadyActiveTransaction) {
    }

    @Override
    public void visit(GenericTransaction genericTransaction) {
    }
}
