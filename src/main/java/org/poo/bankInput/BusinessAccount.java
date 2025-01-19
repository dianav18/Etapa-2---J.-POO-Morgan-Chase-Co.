package org.poo.bankInput;

public class BusinessAccount extends Account{
    /**
     * Instantiates a new Account.
     *
     * @param accountIBAN the account iban
     * @param currency    the currency
     */
    public BusinessAccount(final User owner, final String accountIBAN, final String currency) {
        super(owner, accountIBAN, currency, "business");
    }
}
