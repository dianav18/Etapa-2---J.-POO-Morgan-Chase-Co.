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

@AllArgsConstructor
public class BusinessReportCommand implements CommandHandler {
    private final long startTimestamp;
    private final long endTimestamp;
    private final String account;
    private final String type;
    private final int timestamp;


    @Override
    public void execute(final ArrayNode output) {
        Account checkAccount = Main.getAccount(this.account);
        if (checkAccount == null || !checkAccount.getType().equals("business")) {
            return;
        }

        BusinessAccount account = (BusinessAccount) checkAccount;

        ObjectNode node = output.addObject();

        node.put("command", "businessReport");
        node.put("timestamp", timestamp);
        node = node.putObject("output");

        node.put("balance", account.getBalance());
        node.put("currency", account.getCurrency());
        node.put("deposit limit", account.getDepositLimit());
        node.put("spending limit", account.getSpendingLimit());
        node.put("statistics type", type);

        node.put("IBAN", account.getAccountIBAN());

        if (type.equals("transaction")) {
            executeTransactions(node, account);
            return;
        }

        executeCommerciants(node, account);

    }

    private void executeCommerciants(ObjectNode node, BusinessAccount account) {
        ArrayNode commerciants = node.putArray("commerciants");

        for (Transaction transaction : account.getCommerciantTransactions()) {
            final BusinessTransactionPrinter transactionPrinter = new BusinessTransactionPrinter(commerciants, account);
            transaction.accept(transactionPrinter);
        }

        sortCommerciants(commerciants);
    }

    private void sortCommerciants(ArrayNode commerciants){
        List<ObjectNode> list = new ArrayList<>();
        commerciants.forEach(node -> list.add((ObjectNode) node));

        list.sort(Comparator.comparing(node -> node.get("commerciant").asText()));

        commerciants.removeAll();
        list.forEach(commerciants::add);
    }

    private void executeTransactions(ObjectNode node, BusinessAccount account) {
        node.put("total deposited", 0.0);
        node.put("total spent", 0.0);

        ArrayNode employees = node.putArray("employees");
        ArrayNode managers = node.putArray("managers");

        node.put("balance", account.getBalance());
        node.put("currency", account.getCurrency());
        node.put("deposit limit", account.getDepositLimit());
        node.put("spending limit", account.getSpendingLimit());
        node.put("statistics type", "transaction");
        node.put("total deposited", 0.0);
        node.put("total spent", 0.0);
        node.put("IBAN", account.getAccountIBAN());

        for (BusinessAccount.BusinessUser user : account.getBusinessUsers()) {
            if (user.getRole().equals("employee")) {
                ObjectNode employee = employees.addObject();
                employee.put("deposited", user.getDeposited());
                employee.put("spent", user.getSpent());
                employee.put("username", user.getUsername());

                node.put("total deposited", node.get("total deposited").asDouble() + user.getDeposited());
                node.put("total spent", node.get("total spent").asDouble() + user.getSpent());
            }
            if (user.getRole().equals("manager")) {
                ObjectNode manager = managers.addObject();
                manager.put("deposited", user.getDeposited());
                manager.put("spent", user.getSpent());
                manager.put("username", user.getUsername());

                node.put("total deposited", node.get("total deposited").asDouble() + user.getDeposited());
                node.put("total spent", node.get("total spent").asDouble() + user.getSpent());
            }
        }
    }
}
