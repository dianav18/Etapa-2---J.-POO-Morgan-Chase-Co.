package org.poo.bankInput;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.poo.bankInput.transactions.Transaction;
import org.poo.main.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Account.
 */
/*
 * Class that represents an account.
 * An account has an IBAN, a currency, a type, a balance,
 * a list of cards, an alias, a minimum balance,
 * a list of commerciants, a list of commerciant
 * transactions and a list of transactions.
 * Class is extended by ClassicAccount, SavingsAccount
 * and BusinessAccount.
 */
@Getter
@Setter
public class Account {
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
     * Instantiates a new Account.
     *
     * @param accountIBAN the account iban
     * @param currency    the currency
     * @param type        the type
     */
    public Account(final User owner, final String accountIBAN, final String currency, final String type) {
        this.owner = owner;
        this.accountIBAN = accountIBAN;
        this.currency = currency;
        this.type = type;
        this.balance = 0;
        this.cards = new ArrayList<>();
        this.minBalance = 0;
        commerciants = new ArrayList<>();
        commerciantTransactions = new ArrayList<>();
        transactions = new ArrayList<>();
    }

//    public void setBalance(final double balance) {
//        this.balance = rountToTwoDecimals(balance);
//    }

//    private double rountToTwoDecimals(final double value) {
//        return Math.round(value * 100.0) / 100.0;
//    }

    /**
     * Add card.
     *
     * @param card the card
     */
    public void addCard(final Card card) {
        this.cards.add(card);
    }

    /**
     * Remove card.
     *
     * @param card the card
     */
    public void removeCard(final Card card) {
        this.cards.remove(card);
    }

    /**
     * Add commerciant.
     *
     * @param commerciant the commerciant
     */
    public void addCommerciant(final Commerciant commerciant) {
        this.commerciants.add(commerciant);
    }

    /**
     * Add commerciant transaction.
     *
     * @param transaction the transaction
     */
    public void addCommerciantTransaction(final Transaction transaction) {
        this.commerciantTransactions.add(transaction);
    }

    /**
     * Add transaction.
     *
     * @param transaction the transaction
     */
    public void addTransaction(final Transaction transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountIBAN='" + accountIBAN + '\'' +
                ", currency='" + currency + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void addBalance(User user, double amount, String currency) {
        this.balance += Main.getCurrencyConverter().convert(amount, currency, this.currency);
    }


    public boolean removeBalance(User user, double amount, String currency, String commerciant) {
        double realAmount = Main.getCurrencyConverter().convert(amount, currency, this.currency);
        double commission = Commission.calculateCommission(this, realAmount, this.currency);

        // this is here because Cashback#calculate assumes the user has finished or is able to finish the transaction (i.e. has enough balance)
        if (this.balance - realAmount - commission < 0) {
            return false;
        }

        double cashback = Cashback.calculate(realAmount, this.currency, this, commerciant);

        return removeBalance(user, realAmount, commission, cashback);
    }

    public boolean removeBalance(User user, double amount, double commission, double cashback) {
        if (this.balance - amount - commission < 0) {
            return false;
        }

        System.out.println("Amount:     " + amount);
        System.out.println("Commission: " + commission);
        System.out.println("Cashback:   " + cashback);

        this.balance = this.balance - amount - commission + cashback;
        if (Main.getCurrencyConverter().convert(amount, this.currency, "RON") >= 300) {
            owner.recordTransactionOver300(this);
        }
        return true;
    }

    public List<User> getUsers() {
        return List.of(owner);
    }
}
