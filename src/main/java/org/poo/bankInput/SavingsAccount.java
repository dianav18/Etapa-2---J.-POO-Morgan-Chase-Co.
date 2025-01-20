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
     * @param owner        the owner
     * @param iban         the iban
     * @param currency     the currency
     * @param interestRate the interest rate
     */
    public SavingsAccount(final User owner, final String iban, final String currency,
                          final double interestRate) {
        super(owner, iban, currency, "savings");
        this.interestRate = interestRate;
    }

    /**
     * Sets interest rate.
     *
     * @param interestRate the interest rate
     */
    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Add interest.
     *
     * @param additionalInterestRate the additional interest rate
     */
    public void addInterest(final double additionalInterestRate) {
        addBalance(null, this.getBalance() * interestRate, this.getCurrency());
    }

}
