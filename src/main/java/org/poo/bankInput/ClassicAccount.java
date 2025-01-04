package org.poo.bankInput;

/**
 * The type Classic account.
 */
public class ClassicAccount extends Account {
    /**
     * Instantiates a new Classic account.
     *
     * @param iban     the iban of the account
     * @param currency the currency of the account
     */
    public ClassicAccount(final String iban, final String currency, final String typeOfPlan) {
        super(iban, currency, "classic", typeOfPlan);
    }
}
