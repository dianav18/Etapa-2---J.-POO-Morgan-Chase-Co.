package org.poo.commands;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.User;
import org.poo.handlers.CommandHandler;

import java.util.List;

/**
 * It represents the print users command, which is used to print the users.
 */
public final class PrintUsersCommand implements CommandHandler {
    private final List<User> users;
    private final int timestamp;

    /**
     * Instantiates a new Print users command.
     *
     * @param users     the users
     * @param timestamp the timestamp at which the users are printed
     */
    public PrintUsersCommand(final List<User> users, final int timestamp) {
        this.users = users;
        this.timestamp = timestamp;
    }

    /**
     * Execute.
     *
     * @param output the output
     */
    @Override
    public void execute(final ArrayNode output) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ArrayNode usersArray = objectMapper.createArrayNode();

        for (final User user : users) {
            final ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("firstName", user.getFirstName());
            userNode.put("lastName", user.getLastName());
            userNode.put("email", user.getEmail());

            final ArrayNode accountsArray = objectMapper.createArrayNode();
            for (final Account account : user.getAccounts()) {
                final ObjectNode accountNode = objectMapper.createObjectNode();
                accountNode.put("IBAN", account.getAccountIBAN());
                accountNode.put("balance", account.getBalance());
                accountNode.put("currency", account.getCurrency());
                accountNode.put("type", account.getType());

                final ArrayNode cardsArray = objectMapper.createArrayNode();
                for (final Card card : account.getCards()) {
                    final ObjectNode cardNode = objectMapper.createObjectNode();
                    cardNode.put("cardNumber", card.getCardNumber());
                    cardNode.put("status", card.getStatus());
                    cardsArray.add(cardNode);
                }
                accountNode.set("cards", cardsArray);
                accountsArray.add(accountNode);
            }
            userNode.set("accounts", accountsArray);

            usersArray.add(userNode);
        }

        final ObjectNode commandOutput = objectMapper.createObjectNode();
        commandOutput.put("command", "printUsers");
        commandOutput.set("output", usersArray);
        commandOutput.put("timestamp", timestamp);

        output.add(commandOutput);
    }
}
