package org.poo.bankInput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.poo.main.Main;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessAccount extends Account {

    private List<BusinessUser> businessUsers;
    private double depositLimit;
    private double spendingLimit;

    /**
     * Instantiates a new Account.
     *
     * @param accountIBAN the account iban
     * @param currency    the currency
     */
    public BusinessAccount(final User owner, final String accountIBAN, final String currency) {
        super(owner, accountIBAN, currency, "business");

        this.depositLimit = Main.getCurrencyConverter().convert(500, "RON", currency);
        this.spendingLimit = Main.getCurrencyConverter().convert(500, "RON", currency);

        this.businessUsers = new ArrayList<>();
        addUser(owner, "owner");
    }

    public BusinessUser getBusinessUser(String email) {
        for (BusinessUser user : businessUsers) {
            if (user.getUser().getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    public void addUser(User user, String role) {
        if (getBusinessUser(user.getEmail()) != null) {
            return;
        }

        businessUsers.add(new BusinessUser(user, role));
    }

    @Getter
    @AllArgsConstructor
    public static class BusinessUser {
        private User user;
        private String username;
        private String role;
        private double spent;
        private double deposited;

        public BusinessUser(User user, String role) {
            this.user = user;
            this.username = user.getLastName() + " " + user.getFirstName();
            this.role = role;
            this.spent = 0;
            this.deposited = 0;
        }

        public void addSpent(double amount) {
            this.spent += amount;
        }

        public void addDeposited(double amount) {
            this.deposited += amount;
        }
    }

    @Override
    public void addBalance(User user, double amount, String currency) {
        BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null) {
            return;
        }

        if(businessUser.getRole().equals("employee") && amount > depositLimit){
            return;
        }

        super.addBalance(user, amount, currency);

        businessUser.addDeposited(amount);
    }

    @Override
    public boolean removeBalance(User user, double amount, String currency, String commerciant) {
        BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null) {
            return false;
        }

        if(businessUser.getRole().equals("employee") && amount > spendingLimit){
            return false;
        }

        return super.removeBalance(user, amount, currency, commerciant);
    }

    @Override
    public boolean removeBalance(User user, double amount, double commission, double cashback) {
        BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null) {
            return false;
        }

        if(businessUser.getRole().equals("employee") && amount > spendingLimit){
            return false;
        }

        boolean result = super.removeBalance(user, amount, commission, cashback);
        if (!result) {
            return false;
        }

        businessUser.addSpent(amount);

        return true;
    }

    public boolean setSpendingLimit(User user, double spendingLimit){
        BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null || !businessUser.getRole().equals("owner")) {
            return false;
        }

        this.spendingLimit = spendingLimit;
        return true;
    }

    public boolean setDepositLimit(User user, double depositLimit){
        BusinessUser businessUser = getBusinessUser(user.getEmail());

        if (businessUser == null || !businessUser.getRole().equals("owner")) {
            return false;
        }

        this.depositLimit = depositLimit;
        return true;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        users.add(getOwner());
        for (BusinessUser businessUser : this.businessUsers) {
            users.add(businessUser.getUser());
        }

        return users;
    }
}
