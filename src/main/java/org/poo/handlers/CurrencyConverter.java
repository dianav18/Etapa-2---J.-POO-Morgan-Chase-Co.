package org.poo.handlers;

import org.poo.bankInput.ExchangeRate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Currency converter.
 * It converts an amount from one currency to another.
 */
public class CurrencyConverter {
    private final Map<String, Double> conversionRates;

    /**
     * Instantiates a new Currency converter.
     *
     * @param exchangeRates the exchange rates
     */
    public CurrencyConverter(final List<ExchangeRate> exchangeRates) {
        this.conversionRates = new HashMap<>();

        for (final ExchangeRate rate : exchangeRates) {
            final String key = generateKey(rate.getFrom(), rate.getTo());
            conversionRates.put(key, rate.getRate());

            final String reverseKey = generateKey(rate.getTo(), rate.getFrom());
            if (!conversionRates.containsKey(reverseKey)) {
                conversionRates.put(reverseKey, 1 / rate.getRate());
            }
        }
    }

    /**
     * Converts an amount from one currency to another.
     *
     * @param amount the amount
     * @param from   the source currency
     * @param to     the target currency
     * @return the converted amount or -1 if conversion is not possible
     */
    public double convert(final double amount, final String from, final String to) {
        if (from.equals(to)) {
            return amount;
        }

        final String key = generateKey(from, to);
        if (conversionRates.containsKey(key)) {
            return amount * conversionRates.get(key);
        }

        for (final String intermediate : getAllCurrencies()) {
            final String firstKey = generateKey(from, intermediate);
            final String secondKey = generateKey(intermediate, to);

            if (conversionRates.containsKey(firstKey) && conversionRates.containsKey(secondKey)) {
                final double indirectRate = conversionRates.get(firstKey)
                        * conversionRates.get(secondKey);
                conversionRates.put(key, indirectRate);
                return amount * indirectRate;
            }
        }

        return -1;
    }

    /**
     * Generates a key for the conversion map.
     *
     * @param from the source currency
     * @param to   the target currency
     * @return the generated key
     */
    private String generateKey(final String from, final String to) {
        return from + "->" + to;
    }

    /**
     * Gets all distinct currencies from the conversion rates.
     *
     * @return the list of currencies
     */
    private List<String> getAllCurrencies() {
        final List<String> currencies = new ArrayList<>();

        for (final String key : conversionRates.keySet()) {
            final String[] parts = key.split("->");
            for (final String part : parts) {
                if (!currencies.contains(part)) {
                    currencies.add(part);
                }
            }
        }

        return currencies;
    }
}
