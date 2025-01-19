package org.poo.bankInput;

import org.poo.handlers.CurrencyConverter;
import org.poo.main.Main;

public class Commission {
    public static double calculateCommission(final Account account, final double amount, final String currnecy) {
        // ups :)
        final double ronAmount = Main.getCurrencyConverter().convert(amount, currnecy, "RON");

        if (account.getOwner().getPlan().equals("standard")) {
            return amount * 0.002;
        }
        if (account.getOwner().getPlan().equals("student")) {
            return 0;
        }
        if (account.getOwner().getPlan().equals("silver")) {
            if (ronAmount < 500) {
                return 0;
            }
            return amount * 0.001;
        }
        if (account.getOwner().getPlan().equals("gold")) {
            return  0;
        }
        return 0;
    }
}
