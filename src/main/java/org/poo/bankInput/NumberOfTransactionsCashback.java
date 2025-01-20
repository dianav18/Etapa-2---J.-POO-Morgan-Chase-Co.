package org.poo.bankInput;

import org.poo.main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Number of transactions cashback.
 */
public class NumberOfTransactionsCashback {

    private final HashMap<String, Integer> history = new HashMap<>();
    /**
     * The constant REDEEMED.
     */
    public static final HashMap<String, List<String>> REDEEMED = new HashMap<>();

    /**
     * Gets count.
     *
     * @param iban the iban
     * @return the count
     */
    public int getCount(final String iban) {
        return this.history.getOrDefault(iban, 0);
    }

    /**
     * Is redeemed boolean.
     *
     * @param iban the iban
     * @param type the type
     * @return the boolean
     */
    public static boolean isRedeemed(final String iban, final String type) {
        return REDEEMED.getOrDefault(iban, new ArrayList<>()).contains(type);
    }

    /**
     * Redeemed.
     *
     * @param iban the iban
     * @param type the type
     */
    public static void redeemed(final String iban, final String type) {
        REDEEMED.putIfAbsent(iban, new ArrayList<>());
        REDEEMED.get(iban).add(type);
    }

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
        final Commerciant commerciant = Main.getCommerciant(commerciantID);

        if (commerciant == null) {
            return 0;
        }

        if (commerciant.getCashbackStrategy().equals("nrOfTransactions")) {
            return commerciant.getNumberOfTransactionsCashback().getCashback(
                    amount, currency, account, commerciant);
        }

        return 0;
    }

    private static final double THRESHOLD_FOOD = 2;
    private static final double THRESHOLD_CLOTHES = 5;
    private static final double THRESHOLD_TECH = 10;

    /**
     * Gets threshold.
     *
     * @param commerciantType the commerciant type
     * @return the threshold
     */
    public static double getThreshold(final String commerciantType) {
        return switch (commerciantType) {
            case "Food" -> THRESHOLD_FOOD;
            case "Clothes" -> THRESHOLD_CLOTHES;
            case "Tech" -> THRESHOLD_TECH;
            default -> -1;
        };
    }

    /**
     * Gets cashback percentage.
     *
     * @param commerciantType the commerciant type
     * @return the cashback percentage
     */
    public static double getCashbackPercentage(final String commerciantType) {
        final double percentageFood = 2 / 100.0;
        final double percentageClothes = 5 / 100.0;
        final double percentageTech = 10 / 100.0;

        return switch (commerciantType) {
            case "Food" -> percentageFood;
            case "Clothes" -> percentageClothes;
            case "Tech" -> percentageTech;
            default -> -1;
        };
    }

    /**
     * Gets cashback.
     *
     * @param amount      the amount
     * @param currency    the currency
     * @param account     the account
     * @param commerciant the commerciant
     * @return the cashback
     */
    public double getCashback(final double amount, final String currency, final Account account,
                              final Commerciant commerciant) {
        this.history.putIfAbsent(account.getAccountIBAN(), 0);

        final double cashback = getExistent(amount, currency, account, commerciant);

        this.history.put(account.getAccountIBAN(), this.history.get(account.getAccountIBAN()) + 1);

        return cashback;
    }

    /**
     * Gets existent.
     *
     * @param amount      the amount
     * @param currency    the currency
     * @param account     the account
     * @param commerciant the commerciant
     * @return the existent
     */
    public static double getExistent(final double amount, final String currency,
                                     final Account account, final Commerciant commerciant) {
        for (final NumberOfTransactionsCashback cashback : Main.getCommerciants().stream().map(
                Commerciant::getNumberOfTransactionsCashback).toList()) {
            if ((cashback.getCount(account.getAccountIBAN()) >= getThreshold(
                    commerciant.getCommerciantType()))
                    && !isRedeemed(account.getAccountIBAN(), commerciant.getCommerciantType())) {
                redeemed(account.getAccountIBAN(), commerciant.getCommerciantType());
                return amount * getCashbackPercentage(commerciant.getCommerciantType());
            }
        }

        return 0;
    }
}
