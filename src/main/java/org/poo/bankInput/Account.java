package org.poo.bankInput;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.poo.bankInput.transactions.Transaction;
import org.poo.main.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account. This class contains details about the account owner, IBAN, currency,
 * type, balance, associated cards, and other account-related information. It is extended by
 * ClassicAccount, SavingsAccount, and BusinessAccount.
 */
@Getter
@Setter
public class Account {
    private static final int FREE_UPGRADE_SPENDING_THRESHOLD = 300;

    private User owner;
    private String accountIBAN;
    private String currency;
    private String type;

    @Setter(value = AccessLevel.PRIVATE)
    private double balance;

    private List<Card> cards;
    private String alias = "                          ";
    private double minBalance;
    private List<Commerciant> commerciants;
    private List<Transaction> commerciantTransactions;
    private List<Transaction> transactions;
    private double totalAmountSpent;

    /**
     * Constructs a new Account with the specified details.
     *
     * @param owner       the owner of the account
     * @param accountIBAN the account IBAN
     * @param currency    the currency of the account
     * @param type        the type of the account (e.g., Classic, Savings, Business)
     */
    public Account(final User owner, final String accountIBAN, final String currency,
                   final String type) {
        this.owner = owner;
        this.accountIBAN = accountIBAN;
        this.currency = currency;
        this.type = type;
        this.balance = 0;
        this.cards = new ArrayList<>();
        this.minBalance = 0;
        this.commerciants = new ArrayList<>();
        this.commerciantTransactions = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    /**
     * Adds a card to the account.
     *
     * @param card the card to be added
     */
    public void addCard(final Card card) {
        this.cards.add(card);
    }

    /**
     * Removes a card from the account.
     *
     * @param card the card to be removed
     */
    public void removeCard(final Card card) {
        this.cards.remove(card);
    }

    /**
     * Adds a commerciant to the account.
     *
     * @param commerciant the commerciant to be added
     */
    public void addCommerciant(final Commerciant commerciant) {
        this.commerciants.add(commerciant);
    }

    /**
     * Adds a commerciant transaction to the account.
     *
     * @param transaction the transaction to be added
     */
    public void addCommerciantTransaction(final Transaction transaction) {
        this.commerciantTransactions.add(transaction);
    }

    /**
     * Adds a transaction to the account.
     *
     * @param transaction the transaction to be added
     */
    public void addTransaction(final Transaction transaction) {
        this.transactions.add(transaction);
    }

    /**
     * Adds an amount to the account balance.
     *
     * @param user        the user performing the operation
     * @param amount      the amount to be added
     * @param argCurrency the currency of the amount
     */
    public void addBalance(final User user, final double amount, final String argCurrency) {
        this.balance += Main.getCurrencyConverter().convert(amount, argCurrency, this.currency);
    }

    /**
     * Attempts to remove an amount from the account balance.
     *
     * @param user        the user performing the operation
     * @param amount      the amount to be removed
     * @param argCurrency the currency of the amount
     * @param commerciant the commerciant involved in the transaction
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public boolean removeBalance(final User user, final double amount, final String argCurrency,
                                 final String commerciant) {
        final double realAmount = Main.getCurrencyConverter().convert(amount, argCurrency,
                this.currency);
        final double commission = Commission.calculateCommission(this, realAmount,
                this.currency);

        if (this.balance - realAmount - commission < 0) {
            return false;
        }

        final double cashback = Cashback.calculate(realAmount, this.currency, this,
                commerciant);

        return removeBalance(user, realAmount, commission, cashback);
    }

    /**
     * Removes an amount from the account balance, considering commission and cashback.
     *
     * @param user       the user performing the operation
     * @param amount     the amount to be removed
     * @param commission the commission applied to the transaction
     * @param cashback   the cashback applied to the transaction
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public boolean removeBalance(final User user, final double amount, final double commission,
                                 final double cashback) {
        if (this.balance - amount - commission < 0) {
            return false;
        }

        this.balance = this.balance - amount - commission + cashback;
        if (Main.getCurrencyConverter().convert(amount, this.currency, "RON")
                >= FREE_UPGRADE_SPENDING_THRESHOLD) {
            owner.recordTransactionOver300(this);
        }
        return true;
    }

    /**
     * Retrieves a list of users associated with the account.
     *
     * @return a list containing the account owner
     */
    public List<User> getUsers() {
        return List.of(owner);
    }
}
