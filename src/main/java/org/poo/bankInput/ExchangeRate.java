package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an exchange rate between two currencies.
 */
@Getter
@Setter
public class ExchangeRate {
    private String from;
    private String to;
    private double rate;
    private int timestamp;

    /**
     * Instantiates a new Exchange rate.
     *
     * @param from      the source currency code (e.g., "USD")
     * @param to        the target currency code (e.g., "EUR")
     * @param rate      the exchange rate value (e.g., 0.85 for USD to EUR)
     * @param timestamp the timestamp representing when this rate was set
     */
    public ExchangeRate(final String from, final String to,
                        final double rate, final int timestamp) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.timestamp = timestamp;
    }
}
