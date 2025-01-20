package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.poo.bankInput.transactions.Transaction;
import org.poo.bankInput.transactions.UpgradePlanTransaction;
import org.poo.main.Main;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type User.
 */
@Getter
@Setter
@ToString
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String birthDate;
    private String occupation;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private String plan;
    private int over300Transactions = 0;

    /**
     * Record transaction over 300.
     *
     * @param account the account
     */
    public void recordTransactionOver300(final Account account) {
        if (!plan.equals("silver")) {
            return;
        }
        over300Transactions++;
        freeUpgrade(account);
    }

    private static final int OVER_300_TRANSACTIONS = 5;

    /**
     * Free upgrade.
     *
     * @param account the account
     */
    public void freeUpgrade(final Account account) {
        if (over300Transactions >= OVER_300_TRANSACTIONS) {
            account.addTransaction(new UpgradePlanTransaction(account.getAccountIBAN(),
                    "Upgrade plan", "gold", Main.getTimestamp()));
            plan = "gold";
        }
    }

    /**
     * Instantiates a new User.
     *
     * @param firstName  the first name
     * @param lastName   the last name
     * @param email      the email
     * @param birthDate  the birth date
     * @param occupation the occupation
     */
    public User(final String firstName, final String lastName, final String email,
                final String birthDate, final String occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.occupation = occupation;
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();

        this.plan = "standard";

        if (this.getOccupation().equals("student")) {
            this.plan = "student";
        }
    }

    /**
     * Add account.
     *
     * @param account the account
     */
    public void addAccount(final Account account) {
        this.accounts.add(account);
    }

    /**
     * Remove account.
     *
     * @param account the account
     */
    public void removeAccount(final Account account) {
        this.accounts.remove(account);
    }

    /**
     * Add transaction.
     *
     * @param transaction the transaction to be added
     */
    public void addTransaction(final Transaction transaction) {
        this.transactions.add(transaction);
    }

    private static final long TIMESTAMP_MULTIPLIER = 1000000L;

    /**
     * Gets transactions.
     *
     * @return the transactions
     */
    public List<Transaction> getTransactions() {
        List<Transaction> output = new ArrayList<>(this.transactions);

        for (final Account account : accounts) {
            output.addAll(account.getTransactions());
        }

        output = new ArrayList<>(
                output.stream()
                        .collect(Collectors.toMap(
                                (transaction) -> (transaction.allowsDuplication()
                                        ? UUID.randomUUID() + ":" : "") + (transaction.getClass()
                                        + ":" + transaction.getTimestamp()),
                                Function.identity(),
                                (existing, replacement) -> existing,
                                LinkedHashMap::new
                        ))
                        .values()
                        .stream()
                        .toList()
        );


        output.sort(Comparator.comparingLong(transaction ->
                transaction.getTimestamp() * TIMESTAMP_MULTIPLIER + transaction.order()));

        return output;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return lastName + " " + firstName;
    }
}
