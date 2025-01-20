package org.poo.bankInput;

import org.poo.main.Main;

public final class Commission {
    private Commission() {
    }

    /**
     * Calculates the commission for a transaction based on the account's owner plan.
     *
     * @param account  the account
     * @param amount   the amount of the transaction
     * @param currency the currency of the transaction
     * @return the commission amount
     */
    public static double calculateCommission(final Account account, final double amount,
                                             final String currency) {
        final double ronAmount = Main.getCurrencyConverter().convert(amount, currency, "RON");
        final double standardCommissionPercentage = 0.002;
        final double silverCommissionPercentage = 0.001;
        final double checkAmount = 500;

        switch (account.getOwner().getPlan()) {
            case "standard" -> {
                return amount * standardCommissionPercentage;
            }
            case "student", "gold" -> {
                return 0;
            }
            case "silver" -> {
                if (ronAmount < checkAmount) {
                    return 0;
                }
                return amount * silverCommissionPercentage;
            }
            default -> {
                return 0;
            }
        }
    }
}
