package org.poo.bankInput;

/**
 * The type Cashback.
 */
public final class Cashback {

    private Cashback() {
    }

    /**
     * Calculate double.
     *
     * @param amount      the amount
     * @param currency    the currency
     * @param account     the account
     * @param commerciant the commerciant
     * @return the double
     */
    public static double calculate(final double amount, final String currency,
                                   final Account account, final String commerciant) {
        if (commerciant == null) {
            return 0;
        }

        return SpendingThreshold.checkFor(amount, currency, account, commerciant)
                + NumberOfTransactionsCashback.checkFor(amount, currency, account, commerciant);
    }

}
