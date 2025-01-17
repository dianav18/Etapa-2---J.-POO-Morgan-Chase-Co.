package org.poo.bankInput;

public class Commission {
    public static double calculateCommission(final Account account, final double amount) {
        if (account.getTypeOfPlan().equals("standard")) {
            return amount * 0.002;
        }
        if (account.getTypeOfPlan().equals("student")) {
            return 0;
        }
        if (account.getTypeOfPlan().equals("silver")) {
            if (amount < 500) {
                return 0;
            }
            return amount * 0.001;
        }
        if (account.getTypeOfPlan().equals("gold")) {
            return  0;
        }
        return 0;
    }
}
