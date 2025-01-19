package org.poo.bankInput;

import org.poo.main.Main;

public class SpendingThreshold {
//    private final Account account;
//
//    public SpendingThreshold(final Account account) {
//        this.account = account;
//    }

    public static double checkForByCommerciantName(final double amount, final String currency, final Account account, final String commerciantName){
        for (final Commerciant checkCommerciant : Main.getCommerciants()) {
            if (checkCommerciant.getName().equals(commerciantName)) {
                if (checkCommerciant.getCashbackStrategy().equals("spendingThreshold")) {
                    return SpendingThreshold.getCashback(amount, currency, account);
                }
            }
        }

        return 0;
    }

    public static double checkForByCommerciantIBAN(final double amount, final String currency, final Account account, final String commerciantIBAN){
        return 0; // TODO for later
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
