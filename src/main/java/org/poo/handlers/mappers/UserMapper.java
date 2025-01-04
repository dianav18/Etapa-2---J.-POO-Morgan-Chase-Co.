package org.poo.handlers.mappers;

import org.poo.bankInput.User;
import org.poo.fileio.UserInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User mapper.
 * It maps the users from the user inputs.
 */
public final class UserMapper {
    /**
     * Private constructor to prevent instantiation.
     */
    private UserMapper() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * Map to users list.
     *
     * @param userInputs the user inputs array
     * @return the list of users
     */
    public static List<User> mapToUsers(final UserInput[] userInputs) {
        final List<User> users = new ArrayList<>();
        if (userInputs != null) {
            for (final UserInput userInput : userInputs) {
                users.add(new User(userInput.getFirstName(),
                        userInput.getLastName(),
                        userInput.getEmail(), userInput.getBirthDate(), userInput.getOccupation()));
            }
        }
        return users;
    }
}
