package org.poo.bankInput;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.poo.bankInput.transactions.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    private String Occupation;
    private List<Account> accounts;
    private List<Transaction> transactions;

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     */
    public User(final String firstName, final String lastName, final String email, final String birthDate, final String Occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.Occupation = Occupation;
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
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

    /**
     * Gets transactions.
     *
     * @return the transactions
     */
    public List<Transaction> getTransactions() {
        final List<Transaction> output = new ArrayList<>();

        output.addAll(this.transactions);

        for (final Account account : accounts) {
            output.addAll(account.getTransactions());
        }

        output.sort(Comparator.comparingInt(Transaction::getTimestamp));
        return output;
    }
}
