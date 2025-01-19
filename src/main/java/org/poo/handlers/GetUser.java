package org.poo.handlers;
import org.poo.bankInput.User;

import java.util.List;

public class GetUser {
    private final String email;
    private final List<User> users;

    public GetUser(final String email, final List<User> users) {
        this.email = email;
        this.users = users;
    }


}
