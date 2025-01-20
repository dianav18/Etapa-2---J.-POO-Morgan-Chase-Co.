package org.poo.bankInput;

import lombok.Getter;

/**
 * The type Commerciant.
 */
@Getter
public final class Commerciant {
    private final String name;
    private final int commerciantID;
    private final String commerciantIBAN;
    private final String commerciantType;
    private final String cashbackStrategy;
    private double totalSpent;
    private final NumberOfTransactionsCashback numberOfTransactionsCashback
            = new NumberOfTransactionsCashback();

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

    /**
     * The type Builder.
     */
    public static class Builder {
        private String name;
        private int commerciantID;
        private String commerciantIBAN;
        private String commerciantType;
        private String cashbackStrategy;
        private double totalSpent = 0;

        /**
         * Instantiates a new Builder.
         *
         * @param name the name
         */
        public Builder(final String name) {
            this.name = name;
        }

        /**
         * Commerciant id builder.
         *
         * @param argCommerciantID the commerciant id
         * @return the builder
         */
        public Builder commerciantID(final int argCommerciantID) {
            this.commerciantID = commerciantID;
            return this;
        }

        /**
         * Commerciant iban builder.
         *
         * @param argCommerciantIBAN the commerciant iban
         * @return the builder
         */
        public Builder commerciantIBAN(final String argCommerciantIBAN) {
            this.commerciantIBAN = argCommerciantIBAN;
            return this;
        }

        /**
         * Commerciant type builder.
         *
         * @param argCommerciantType the commerciant type
         * @return the builder
         */
        public Builder commerciantType(final String argCommerciantType) {
            this.commerciantType = argCommerciantType;
            return this;
        }

        /**
         * Cashback strategy builder.
         *
         * @param argCashbackStrategy the cashback strategy
         * @return the builder
         */
        public Builder cashbackStrategy(final String argCashbackStrategy) {
            this.cashbackStrategy = argCashbackStrategy;
            return this;
        }

        /**
         * Total spent builder.
         *
         * @param argTotalSpent the total spent
         * @return the builder
         */
        public Builder totalSpent(final double argTotalSpent) {
            this.totalSpent = argTotalSpent;
            return this;
        }

        /**
         * Build commerciant.
         *
         * @return the commerciant
         */
        public Commerciant build() {
            return new Commerciant(this);
        }
    }
}
