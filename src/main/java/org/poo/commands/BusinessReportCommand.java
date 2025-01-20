package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.BusinessAccount;
import org.poo.bankInput.transactions.BusinessTransactionPrinter;
import org.poo.bankInput.transactions.Transaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type Business report command.
 */
@AllArgsConstructor
public class BusinessReportCommand implements CommandHandler {
    private final long startTimestamp;
    private final long endTimestamp;
    private final String account;
    private final String type;
    private final int timestamp;


    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final Account checkAccount = Main.getAccount(this.account);
        if (checkAccount == null || !checkAccount.getType().equals("business")) {
            return;
        }

        final BusinessAccount businessAccount = (BusinessAccount) checkAccount;

        ObjectNode node = output.addObject();

        node.put("command", "businessReport");
        node.put("timestamp", timestamp);
        node = node.putObject("output");

        node.put("balance", businessAccount.getBalance());
        node.put("currency", businessAccount.getCurrency());
        node.put("deposit limit", businessAccount.getDepositLimit());
        node.put("spending limit", businessAccount.getSpendingLimit());
        node.put("statistics type", type);

        node.put("IBAN", businessAccount.getAccountIBAN());

        if (type.equals("transaction")) {
            executeTransactions(node, businessAccount);
            return;
        }

        executeCommerciants(node, businessAccount);

    }

    private void executeCommerciants(final ObjectNode node, final BusinessAccount argAccount) {
        final ArrayNode commerciants = node.putArray("commerciants");

        for (final Transaction transaction : argAccount.getCommerciantTransactions()) {
            final BusinessTransactionPrinter transactionPrinter =
                    new BusinessTransactionPrinter(commerciants,
                    argAccount);
            transaction.accept(transactionPrinter);
        }

        sortCommerciants(commerciants);
    }

    private void sortCommerciants(final ArrayNode commerciants) {
        final List<ObjectNode> list = new ArrayList<>();
        commerciants.forEach(node -> list.add((ObjectNode) node));

        list.sort(Comparator.comparing(node -> node.get("commerciant").asText()));

        commerciants.removeAll();
        list.forEach(commerciants::add);
    }

    private void executeTransactions(final ObjectNode node, final BusinessAccount argAccount) {
        node.put("total deposited", 0.0);
        node.put("total spent", 0.0);

        final ArrayNode employees = node.putArray("employees");
        final ArrayNode managers = node.putArray("managers");

        node.put("balance", argAccount.getBalance());
        node.put("currency", argAccount.getCurrency());
        node.put("deposit limit", argAccount.getDepositLimit());
        node.put("spending limit", argAccount.getSpendingLimit());
        node.put("statistics type", "transaction");
        node.put("total deposited", 0.0);
        node.put("total spent", 0.0);
        node.put("IBAN", argAccount.getAccountIBAN());

        for (final BusinessAccount.BusinessUser user : argAccount.getBusinessUsers()) {
            if (user.getRole().equals("employee")) {
                final ObjectNode employee = employees.addObject();
                employee.put("deposited", user.getDeposited());
                employee.put("spent", user.getSpent());
                employee.put("username", user.getUsername());

                node.put("total deposited", node.get("total deposited").asDouble()
                        + user.getDeposited());
                node.put("total spent", node.get("total spent").asDouble() + user.getSpent());
            }
            if (user.getRole().equals("manager")) {
                final ObjectNode manager = managers.addObject();
                manager.put("deposited", user.getDeposited());
                manager.put("spent", user.getSpent());
                manager.put("username", user.getUsername());

                node.put("total deposited", node.get("total deposited").asDouble()
                        + user.getDeposited());
                node.put("total spent", node.get("total spent").asDouble() + user.getSpent());
            }
        }
    }
}
