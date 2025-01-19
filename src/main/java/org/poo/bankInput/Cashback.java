package org.poo.bankInput;

import org.poo.main.Main;

public class Cashback {

    public static double calculateByCommerciantName(final double amount, final String currency, final Account account, final String commerciantName){
        return SpendingThreshold.checkForByCommerciantName(amount, currency, account, commerciantName) +
                0 // TODO number of transactions
                ;
    }

    public static double calculateByCommerciantIBAN(final double amount, final String currency, final Account account, final String commerciantIBAN){
        return SpendingThreshold.checkForByCommerciantIBAN(amount, currency, account, commerciantIBAN) +
                0 // TODO number of transactions
                ;
    }

}
