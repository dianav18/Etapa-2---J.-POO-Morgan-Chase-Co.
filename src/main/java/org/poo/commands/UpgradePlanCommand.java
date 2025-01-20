package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.bankInput.transactions.InsufficientFundsTransaction;
import org.poo.bankInput.transactions.PlanAlreadyActiveTransaction;
import org.poo.bankInput.transactions.UpgradePlanTransaction;
import org.poo.handlers.CommandHandler;
import org.poo.main.Main;

/**
 * The type Upgrade plan command.
 */
@AllArgsConstructor
public class UpgradePlanCommand implements CommandHandler {

    private final String newPlanType;
    private final String accountIBAN;
    private final int timestamp;

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final Account account = Main.getAccount(accountIBAN);

        if (account == null) {
            final ObjectNode node = output.addObject();
            node.put("command", "upgradePlan");
            node.put("timestamp", timestamp);
            final ObjectNode outputNode = node.putObject("output");
            outputNode.put("description", "Account not found");
            outputNode.put("timestamp", timestamp);
            return;
        }

        final User user = account.getOwner();

        final double upgradeFee = getUpgradeFee(account.getOwner().getPlan(), newPlanType);
        final double finalUpgradeFee;
        finalUpgradeFee = Main.getCurrencyConverter().convert(upgradeFee, "RON",
                account.getCurrency());

        if (upgradeFee == -1) {
            account.addTransaction(new PlanAlreadyActiveTransaction(newPlanType, timestamp));
            return;
        }

        if (!account.removeBalance(user, finalUpgradeFee, 0, 0)) {
            account.addTransaction(new InsufficientFundsTransaction(timestamp));
            return;
        }

        user.setPlan(newPlanType);

        account.addTransaction(new UpgradePlanTransaction(accountIBAN, "Upgrade plan",
                newPlanType, timestamp));
    }

    private static final double SILVER_FEE = 100;
    private static final double GOLD_FEE = 350;
    private static final double GOLD_FEE_CHEAPER = 250;

    private double getUpgradeFee(final String currentPlan, final String newPlan) {
        if (currentPlan.equals("standard") || currentPlan.equals("student")) {
            if (newPlan.equals("silver")) {
                return SILVER_FEE;
            }
            if (newPlan.equals("gold")) {
                return GOLD_FEE;
            }
        }
        if (currentPlan.equals("silver") && newPlan.equals("gold")) {
            return GOLD_FEE_CHEAPER;
        }
        return -1;
    }
}
