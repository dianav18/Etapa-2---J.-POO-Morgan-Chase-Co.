package org.poo.bankInput;

import org.poo.main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumberOfTransactionsCashback {

    private final HashMap<String, Integer> history = new HashMap<>();
    public static final HashMap<String, List<String>> REDEEMED = new HashMap<>();

    public int getCount(final String iban) {
        return this.history.getOrDefault(iban, 0);
    }

    public static boolean isRedeemed(final String iban, final String type) {
        return REDEEMED.getOrDefault(iban, new ArrayList<>()).contains(type);
    }

    public static void redeemed(final String iban, final String type) {
        REDEEMED.putIfAbsent(iban, new ArrayList<>());
        REDEEMED.get(iban).add(type);
    }

    public static double checkFor(final double amount, final String currency, final Account account, final String commerciantID) {
        Commerciant commerciant = Main.getCommerciant(commerciantID);

        if (commerciant == null) {
            return 0;
        }

        if (commerciant.getCashbackStrategy().equals("nrOfTransactions")) {
            return commerciant.getNumberOfTransactionsCashback().getCashback(amount, currency, account, commerciant);
        }

        return 0;
    }

    public static double getThreshold(String commerciantType) {
        return switch (commerciantType) {
            case "Food" -> 2;
            case "Clothes" -> 5;
            case "Tech" -> 10;
            default -> -1;
        };
    }

    public static double getCashbackPercentage(String commerciantType) {
        return switch (commerciantType) {
            case "Food" -> 2 / 100.0;
            case "Clothes" -> 5 / 100.0;
            case "Tech" -> 10 / 100.0;
            default -> -1;
        };
    }

    public double getCashback(final double amount, final String currency, final Account account, Commerciant commerciant) {
        this.history.putIfAbsent(account.getAccountIBAN(), 0);

        double cashback = getExistent(amount, currency, account, commerciant);

        this.history.put(account.getAccountIBAN(), this.history.get(account.getAccountIBAN()) + 1);

        return cashback;
    }

    public static double getExistent(final double amount, final String currency, final Account account, Commerciant commerciant) {
        for (final NumberOfTransactionsCashback cashback : Main.getCommerciants().stream().map(Commerciant::getNumberOfTransactionsCashback).toList()) {
            if ((cashback.getCount(account.getAccountIBAN()) >= getThreshold(commerciant.getCommerciantType())) &&
                    !isRedeemed(account.getAccountIBAN(), commerciant.getCommerciantType())) {
                redeemed(account.getAccountIBAN(), commerciant.getCommerciantType());
                return amount * getCashbackPercentage(commerciant.getCommerciantType());
            }
        }

        return 0;
    }
}
