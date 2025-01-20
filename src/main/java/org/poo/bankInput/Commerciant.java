package org.poo.bankInput;

import lombok.Getter;

/**
 * The type Commerciant.
 */
@Getter
public class Commerciant {
    private String name;
    private int commerciantID;
    private String commerciantIBAN;
    private String commerciantType;
    private String cashbackStrategy;
    private double totalSpent;
    private NumberOfTransactionsCashback numberOfTransactionsCashback = new NumberOfTransactionsCashback();

    private Commerciant(final Builder builder) {
        this.name = builder.name;
        this.commerciantID = builder.commerciantID;
        this.commerciantIBAN = builder.commerciantIBAN;
        this.commerciantType = builder.commerciantType;
        this.cashbackStrategy = builder.cashbackStrategy;
        this.totalSpent = builder.totalSpent;
    }

    /**
     * Add spent amount.
     *
     * @param amount the amount
     */
    public void addSpentAmount(final double amount) {
        totalSpent += amount;
    }

    public static class Builder {
        private String name;
        private int commerciantID;
        private String commerciantIBAN;
        private String commerciantType;
        private String cashbackStrategy;
        private double totalSpent = 0;

        public Builder(final String name) {
            this.name = name;
        }

        public Builder commerciantID(final int commerciantID) {
            this.commerciantID = commerciantID;
            return this;
        }

        public Builder commerciantIBAN(final String commerciantIBAN) {
            this.commerciantIBAN = commerciantIBAN;
            return this;
        }

        public Builder commerciantType(final String commerciantType) {
            this.commerciantType = commerciantType;
            return this;
        }

        public Builder cashbackStrategy(final String cashbackStrategy) {
            this.cashbackStrategy = cashbackStrategy;
            return this;
        }

        public Builder totalSpent(final double totalSpent) {
            this.totalSpent = totalSpent;
            return this;
        }

        public Commerciant build() {
            return new Commerciant(this);
        }
    }
}
