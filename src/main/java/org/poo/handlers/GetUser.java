package org.poo.handlers;
import org.poo.bankInput.User;

import java.util.List;

/**
 * The type Get user.
 */
public class GetUser {
    private final String email;
    private final List<User> users;

    /**
     * Instantiates a new Get user.
     *
     * @param email the email
     * @param users the users
     */
    public GetUser(final String email, final List<User> users) {
        this.email = email;
        this.users = users;
    }


}
