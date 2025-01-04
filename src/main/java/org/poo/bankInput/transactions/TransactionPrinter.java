package org.poo.bankInput.transactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Transaction printer.
 * This class is used to print the details of a transaction to a JSON object.
 */
public final class TransactionPrinter implements TransactionVisitor {
    private final ArrayNode output;

    /**
     * Instantiates a new Transaction printer.
     *
     * @param output the output JSON object to which the transaction details will be printed.
     */
    public TransactionPrinter(final ArrayNode output) {
        this.output = output;
    }

    @Override
    public void visit(final AccountCreatedTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("description", transaction.getDescription());
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final SentTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("description", transaction.getDescription());
        node.put("amount", transaction.getAmount() + " " + transaction.getCurrency());
        node.put("senderIBAN", transaction.getSenderIBAN());
        node.put("receiverIBAN", transaction.getReceiverIBAN());
        node.put("timestamp", transaction.getTimestamp());
        node.put("transferType", "sent");
    }

    @Override
    public void visit(final CardCreatedTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("account", transaction.getAccount());
        node.put("card", transaction.getCard());
        node.put("cardHolder", transaction.getCardHolder());
        node.put("description", transaction.getDescription());
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final CardPaymentTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("amount", transaction.getAmount());
        node.put("commerciant", transaction.getCommerciant());
        node.put("description", "Card payment"); //todo
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final InsufficientFundsTransaction transaction) {
        final ObjectNode node = output.addObject();
        node.put("description", "Insufficient funds");//todo
        node.put("timestamp", transaction.getTimestamp());
    }

    @Override
    public void visit(final CardDestroyedTransaction cardDestroyedTransaction) {
        final ObjectNode node = output.addObject();
        node.put("account", cardDestroyedTransaction.getAccount());
        node.put("card", cardDestroyedTransaction.getCard());
        node.put("cardHolder", cardDestroyedTransaction.getEmail());
        node.put("description", "The card has been destroyed");
        node.put("timestamp", cardDestroyedTransaction.getTimestamp());
    }

    @Override
    public void visit(final CardFrozenTransaction cardFrozenTransaction) {
        final ObjectNode node = output.addObject();
        node.put("description", "The card is frozen"); //todo
        node.put("timestamp", cardFrozenTransaction.getTimestamp());
    }

    @Override
    public void visit(final AccountWarningTransaction accountWaringTransaction) {
        final ObjectNode node = output.addObject();
        node.put("description",
                "You have reached the minimum amount of funds, the card will be frozen"); //todo
        node.put("timestamp", accountWaringTransaction.getTimestamp());
    }

    @Override
    public void visit(final SplitPaymentTransaction splitPaymentTransaction) {
        final ObjectNode node = output.addObject();

        node.put("description", String.format("Split payment of %.2f %s",
                splitPaymentTransaction.getTotalAmount(), splitPaymentTransaction.getCurrency())); //todo
        node.put("currency", splitPaymentTransaction.getCurrency());

        if (splitPaymentTransaction.isShowError()) {
            node.put("error", "Account "
                    + splitPaymentTransaction.getProblematicAccountIBAN()
                    + " has insufficient funds for a split payment.");
        }

        final ArrayNode involvedAccountsNode = node.putArray("involvedAccounts");
        for (final String account : splitPaymentTransaction.getInvolvedAccounts()) {
            involvedAccountsNode.add(account);
        }

        node.put("amount", splitPaymentTransaction.getAmountPerAccount());
        node.put("timestamp", splitPaymentTransaction.getTimestamp());
    }

    @Override
    public void visit(final CommerciantTransaction commerciantTransaction) {
        final ObjectNode node = output.addObject();
        node.put("amount", commerciantTransaction.getAmount());
        node.put("commerciant", commerciantTransaction.getCommerciant());
        node.put("description", "Card payment"); //todo
        node.put("timestamp", commerciantTransaction.getTimestamp());
    }

    @Override
    public void visit(final ReceivedTransaction receivedTransaction) {
            final ObjectNode node = output.addObject();
            node.put("description", receivedTransaction.getDescription());
            node.put("amount", receivedTransaction.getAmount()
                    + " " + receivedTransaction.getCurrency());
            node.put("senderIBAN", receivedTransaction.getSenderIBAN());
            node.put("receiverIBAN", receivedTransaction.getReceiverIBAN());
            node.put("timestamp", receivedTransaction.getTimestamp());
            node.put("transferType", "received");
    }

    @Override
    public void visit(final CannotDeleteAccountTransaction cannotDeleteAccountTransaction) {
        final ObjectNode node = output.addObject();
        node.put("description", "Account couldn't be deleted - there are funds remaining"); //todo
        node.put("timestamp", cannotDeleteAccountTransaction.getTimestamp());
    }

    @Override
    public void visit(final ChangeInterestRateTransaction changeInterestRateTransaction) {
        final ObjectNode node = output.addObject();
        node.put("description", changeInterestRateTransaction.getDescription());
        node.put("timestamp", changeInterestRateTransaction.getTimestamp());
    }

    @Override
    public void visit(final WithdrawSavingsTransaction withdrawSavingsTransaction) {
        final ObjectNode node = output.addObject();
        node.put("description", withdrawSavingsTransaction.getDescription());
        node.put("timestamp", withdrawSavingsTransaction.getTimestamp());
    }

}
