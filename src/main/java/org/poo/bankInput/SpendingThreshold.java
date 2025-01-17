package org.poo.bankInput;

public class SpendingThreshold {
//    private final Account account;
//
//    public SpendingThreshold(final Account account) {
//        this.account = account;
//    }

    public static double getCashback(final double amount, final Account account) {
        if (account.getTotalAmountSpent() >= 500) {
            if (account.getTypeOfPlan().equals("standard") || account.getTypeOfPlan().equals("student")) {
                return 0.25 / 100 * amount;
            }
            if (account.getTypeOfPlan().equals("silver")) {
                return amount * 0.5 / 100;
            }
            if (account.getTypeOfPlan().equals("gold")) {
                return amount * 0.7 / 100;
            }
        }

        if (account.getTotalAmountSpent() >= 300) {
            if (account.getTypeOfPlan().equals("standard") || account.getTypeOfPlan().equals("student")) {
                return 0.2 / 100 * amount;
            }
            if (account.getTypeOfPlan().equals("silver")) {
                return amount * 0.4 / 100;
            }
            if (account.getTypeOfPlan().equals("gold")) {
                return amount * 0.55 / 100;
            }
        }

        if (account.getTotalAmountSpent() >= 100) {
            if (account.getTypeOfPlan().equals("standard") || account.getTypeOfPlan().equals("student")) {
                return 0.1 / 100 * amount;
            }
            if (account.getTypeOfPlan().equals("silver")) {
                return amount * 0.3 / 100;
            }
            if (account.getTypeOfPlan().equals("gold")) {
                return amount * 0.5 / 100;
            }
        }
        return 0;
    }
}
