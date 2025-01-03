package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Savings account.
 */
@Setter
@Getter
public class SavingsAccount extends Account {
    private double interestRate;

    /**
     * Instantiates a new Savings account.
     *
     * @param iban         the iban
     * @param currency     the currency
     * @param interestRate the interest rate
     */
    public SavingsAccount(final String iban, final String currency, final double interestRate) {
        super(iban, currency, "savings");
        this.interestRate = interestRate;
    }

    /**
     * Add interest.
     *
     * @param additionalInterestRate the additional interest rate
     */
    public void addInterest(final double additionalInterestRate) {
        this.interestRate += additionalInterestRate;
    }

}
