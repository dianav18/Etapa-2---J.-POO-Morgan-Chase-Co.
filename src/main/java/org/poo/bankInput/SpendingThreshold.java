package org.poo.bankInput;

import org.poo.main.Main;

/**
 * The type Spending threshold.
 */
public final class SpendingThreshold {

    private SpendingThreshold() {
    }

    // Constants for threshold limits
    private static final double THRESHOLD_LIMIT_500 = 500.0;
    private static final double THRESHOLD_LIMIT_300 = 300.0;
    private static final double THRESHOLD_LIMIT_100 = 100.0;

    // Cashback percentages for different plans
    private static final double STANDARD_STUDENT_CASHBACK_500 = 0.25 / 100;
    private static final double SILVER_CASHBACK_500 = 0.5 / 100;
    private static final double GOLD_CASHBACK_500 = 0.7 / 100;

    private static final double STANDARD_STUDENT_CASHBACK_300 = 0.2 / 100;
    private static final double SILVER_CASHBACK_300 = 0.4 / 100;
    private static final double GOLD_CASHBACK_300 = 0.55 / 100;

    private static final double STANDARD_STUDENT_CASHBACK_100 = 0.1 / 100;
    private static final double SILVER_CASHBACK_100 = 0.3 / 100;
    private static final double GOLD_CASHBACK_100 = 0.5 / 100;

    /**
     * Check for double.
     *
     * @param amount        the amount
     * @param currency      the currency
     * @param account       the account
     * @param commerciantID the commerciant id
     * @return the double
     */
    public static double checkFor(final double amount, final String currency,
                                  final Account account, final String commerciantID) {
        double cashback = 0;

        final Commerciant commerciant = Main.getCommerciant(commerciantID);

        if (commerciant == null) {
            return cashback;
        }

        if (commerciant.getCashbackStrategy().equals("spendingThreshold")) {
            cashback = SpendingThreshold.getCashback(amount, currency, account);
        }

        return cashback + NumberOfTransactionsCashback.getExistent(amount, currency,
                account, commerciant);
    }

    /**
     * Gets cashback.
     *
     * @param amount   the amount
     * @param currency the currency
     * @param account  the account
     * @return the cashback
     */
    public static double getCashback(final double amount, final String currency,
                                     final Account account) {

        final double ronAmount = Main.getCurrencyConverter().convert(amount, currency, "RON");
        account.setTotalAmountSpent(account.getTotalAmountSpent() + ronAmount);

        if (account.getTotalAmountSpent() >= THRESHOLD_LIMIT_500) {
            return calculateCashback(amount, account, THRESHOLD_LIMIT_500);
        }

        if (account.getTotalAmountSpent() >= THRESHOLD_LIMIT_300) {
            return calculateCashback(amount, account, THRESHOLD_LIMIT_300);
        }

        if (account.getTotalAmountSpent() >= THRESHOLD_LIMIT_100) {
            return calculateCashback(amount, account, THRESHOLD_LIMIT_100);
        }

        return 0;
    }

    /**
     * Calculate cashback based on the amount spent and account plan.
     *
     * @param amount    the amount
     * @param account   the account
     * @param threshold the threshold limit
     * @return the cashback
     */
    private static double calculateCashback(final double amount,
                                            final Account account, final double threshold) {
        final String plan = account.getOwner().getPlan();
        return switch (plan) {
            case "standard", "student" -> amount * (
                    getCashbackPercentageForStandardStudent(threshold));
            case "silver" -> amount * (getCashbackPercentageForSilver(threshold));
            case "gold" -> amount * (getCashbackPercentageForGold(threshold));
            default -> 0;
        };
    }

    private static double getCashbackPercentageForStandardStudent(final double threshold) {
        if (threshold == THRESHOLD_LIMIT_500) {
            return STANDARD_STUDENT_CASHBACK_500;
        }
        if (threshold == THRESHOLD_LIMIT_300) {
            return STANDARD_STUDENT_CASHBACK_300;
        }

        return STANDARD_STUDENT_CASHBACK_100;
    }

    private static double getCashbackPercentageForSilver(final double threshold) {
        if (threshold == THRESHOLD_LIMIT_500) {
            return SILVER_CASHBACK_500;
        }
        if (threshold == THRESHOLD_LIMIT_300) {
            return SILVER_CASHBACK_300;
        }
        return SILVER_CASHBACK_100;
    }

    private static double getCashbackPercentageForGold(final double threshold) {
        if (threshold == THRESHOLD_LIMIT_500) {
            return GOLD_CASHBACK_500;
        }
        if (threshold == THRESHOLD_LIMIT_300) {
            return GOLD_CASHBACK_300;
        }
        return GOLD_CASHBACK_100;
    }
}
