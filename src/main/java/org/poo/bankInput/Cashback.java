package org.poo.bankInput;

public class Cashback {

    public static double calculate(final double amount, final String currency, final Account account, final String commerciant) {
        if (commerciant == null) {
            return 0;
        }

        return SpendingThreshold.checkFor(amount, currency, account, commerciant) +
                NumberOfTransactionsCashback.checkFor(amount, currency, account, commerciant);
    }

}
