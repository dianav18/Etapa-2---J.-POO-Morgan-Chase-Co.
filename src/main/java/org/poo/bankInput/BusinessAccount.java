package org.poo.bankInput;

public class BusinessAccount extends Account{
    /**
     * Instantiates a new Account.
     *
     * @param accountIBAN the account iban
     * @param currency    the currency
     */
    public BusinessAccount(final String accountIBAN, final String currency, final String typeOfPlan) {
        super(accountIBAN, currency, "business", typeOfPlan);
    }
}
