package org.poo.bankInput;

import org.poo.handlers.CurrencyConverter;
import org.poo.main.Main;

public class Commission {
    public static double calculateCommission(final Account account, final double amount, final String currency) {
        final double ronAmount = Main.getCurrencyConverter().convert(amount, currency, "RON");

        switch (account.getOwner().getPlan()) {
            case "standard" -> {
                return amount * 0.002;
            }
            case "student", "gold" -> {
                return 0;
            }
            case "silver" -> {
                if (ronAmount < 500) {
                    return 0;
                }
                return amount * 0.001;
            }
        }
        return 0;
    }
}
