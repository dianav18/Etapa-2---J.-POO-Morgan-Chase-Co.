package org.poo.bankInput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.poo.main.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a business account, which is an extension of the Account class.
 * This account type allows multiple business users, has spending and deposit limits,
 * and provides additional role-based functionalities.
 */
@Getter
public class BusinessAccount extends Account {
    private static final int FREE_UPGRADE_SPENDING_THRESHOLD = 500;

    private final List<BusinessUser> businessUsers;
    private double depositLimit;
    private double spendingLimit;

    /**
     * Constructs a new BusinessAccount.
     *
     * @param owner       the owner of the account
     * @param accountIBAN the account IBAN
     * @param currency    the currency of the account
     */
    public BusinessAccount(final User owner, final String accountIBAN, final String currency) {
        super(owner, accountIBAN, currency, "business");

        this.depositLimit = Main.getCurrencyConverter().convert(FREE_UPGRADE_SPENDING_THRESHOLD,
                "RON", currency);
        this.spendingLimit = Main.getCurrencyConverter().convert(FREE_UPGRADE_SPENDING_THRESHOLD,
                "RON", currency);

        this.businessUsers = new ArrayList<>();
        addUser(owner, "owner");
    }

    /**
     * Retrieves a business user by their email.
     *
     * @param email the email of the business user
     * @return the BusinessUser object if found, otherwise {@code null}
     */
    public BusinessUser getBusinessUser(final String email) {
        for (final BusinessUser user : businessUsers) {
            if (user.getUser().getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Adds a new business user to the account.
     *
     * @param user the user to be added
     * @param role the role of the user (e.g., "owner", "employee")
     */
    public void addUser(final User user, final String role) {
        if (getBusinessUser(user.getEmail()) != null) {
            return;
        }

        businessUsers.add(new BusinessUser(user, role));
    }

    /**
     * Represents a business user associated with the account.
     */
    @Getter
    @AllArgsConstructor
    public static class BusinessUser {
        private User user;
        private String username;
        private String role;
        private double spent;
        private double deposited;

        /**
         * Constructs a new BusinessUser with default spending and deposit values.
         *
         * @param user the user
         * @param role the role of the user
         */
        public BusinessUser(final User user, final String role) {
            this.user = user;
            this.username = user.getLastName() + " " + user.getFirstName();
            this.role = role;
            this.spent = 0;
            this.deposited = 0;
        }

        /**
         * Adds to the total amount spent by the business user.
         *
         * @param amount the amount to add
         */
        public void addSpent(final double amount) {
            this.spent += amount;
        }

        /**
         * Adds to the total amount deposited by the business user.
         *
         * @param amount the amount to add
         */
        public void addDeposited(final double amount) {
            this.deposited += amount;
        }
    }

    /**
     * Adds an amount to the account balance if the deposit is within the allowed limit.
     *
     * @param user     the user performing the operation
     * @param amount   the amount to be added
     * @param currency the currency of the amount
     */
    @Override
    public void addBalance(final User user, final double amount, final String currency) {
        final BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null) {
            return;
        }

        if (businessUser.getRole().equals("employee") && amount > depositLimit) {
            return;
        }

        super.addBalance(user, amount, currency);

        businessUser.addDeposited(amount);
    }

    /**
     * Removes an amount from the account balance if within the allowed spending limit.
     *
     * @param user        the user performing the operation
     * @param amount      the amount to be removed
     * @param argCurrency the currency of the amount
     * @param commerciant the commerciant involved in the transaction
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    @Override
    public boolean removeBalance(final User user, final double amount, final String argCurrency,
                                 final String commerciant) {
        final BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null) {
            return false;
        }

        if (businessUser.getRole().equals("employee") && amount > spendingLimit) {
            return false;
        }

        return super.removeBalance(user, amount, argCurrency, commerciant);
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
    @Override
    public boolean removeBalance(final User user, final double amount, final double commission,
                                 final double cashback) {
        final BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null) {
            return false;
        }

        if (businessUser.getRole().equals("employee") && amount > spendingLimit) {
            return false;
        }

        final boolean result = super.removeBalance(user, amount, commission, cashback);
        if (!result) {
            return false;
        }

        businessUser.addSpent(amount);

        return true;
    }

    /**
     * Sets the spending limit for the account.
     *
     * @param user             the user performing the operation
     * @param argSpendingLimit the new spending limit
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public boolean setSpendingLimit(final User user, final double argSpendingLimit) {
        final BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null || !businessUser.getRole().equals("owner")) {
            return false;
        }

        this.spendingLimit = argSpendingLimit;
        return true;
    }

    /**
     * Sets the deposit limit for the account.
     *
     * @param user             the user performing the operation
     * @param argSpendingLimit the new deposit limit
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public boolean setDepositLimit(final User user, final double argSpendingLimit) {
        final BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null || !businessUser.getRole().equals("owner")) {
            return false;
        }

        this.depositLimit = argSpendingLimit;
        return true;
    }

    /**
     * Retrieves a list of all users associated with the account, including business users.
     *
     * @return a list of users
     */
    @Override
    public List<User> getUsers() {
        final List<User> users = new ArrayList<>();

        users.add(getOwner());
        for (final BusinessUser businessUser : this.businessUsers) {
            users.add(businessUser.getUser());
        }

        return users;
    }
}
