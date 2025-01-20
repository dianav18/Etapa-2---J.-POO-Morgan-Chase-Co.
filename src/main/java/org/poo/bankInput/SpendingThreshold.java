package org.poo.bankInput;

import org.poo.main.Main;

public class SpendingThreshold {


    public static double checkFor(final double amount, final String currency, final Account account, final String commerciantID) {
        double cashback = 0;

        Commerciant commerciant = Main.getCommerciant(commerciantID);

        if (commerciant == null) {
            return cashback;
        }

        if (commerciant.getCashbackStrategy().equals("spendingThreshold")) {
            cashback = SpendingThreshold.getCashback(amount, currency, account);
        }

        return cashback + NumberOfTransactionsCashback.getExistent(amount, currency, account, commerciant);
    }

    public static double getCashback(final double amount, final String currency, final Account account) {
        final double ronAmount = Main.getCurrencyConverter().convert(amount, currency, "RON");
        account.setTotalAmountSpent(account.getTotalAmountSpent() + ronAmount);

        if (account.getTotalAmountSpent() >= 500) {
            switch (account.getOwner().getPlan()) {
                case "standard", "student" -> {
                    return amount * 0.25 / 100;
                }
                case "silver" -> {
                    return amount * 0.5 / 100;
                }
                case "gold" -> {
                    return amount * 0.7 / 100;
                }
            }
        }

        if (account.getTotalAmountSpent() >= 300) {
            switch (account.getOwner().getPlan()) {
                case "standard", "student" -> {
                    return amount * 0.2 / 100;
                }
                case "silver" -> {
                    return amount * 0.4 / 100;
                }
                case "gold" -> {
                    return amount * 0.55 / 100;
                }
            }
        }

        if (account.getTotalAmountSpent() >= 100) {
            switch (account.getOwner().getPlan()) {
                case "standard", "student" -> {
                    return amount * 0.1 / 100;
                }
                case "silver" -> {
                    return amount * 0.3 / 100;
                }
                case "gold" -> {
                    return amount * 0.5 / 100;
                }
            }
        }
        return 0;
    }
}
