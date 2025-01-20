package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import org.poo.bankInput.Account;
import org.poo.bankInput.Card;
import org.poo.bankInput.Commerciant;
import org.poo.bankInput.ExchangeRate;
import org.poo.bankInput.NumberOfTransactionsCashback;
import org.poo.bankInput.User;
import org.poo.checker.Checker;
import org.poo.checker.CheckerConstants;
import org.poo.commands.SplitPaymentCommand;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.handlers.AccountExtractor;
import org.poo.handlers.CommandHandler;
import org.poo.handlers.CommandInvoker;
import org.poo.handlers.CommandLogicFactory;
import org.poo.handlers.CurrencyConverter;
import org.poo.handlers.mappers.CommerciantMapper;
import org.poo.handlers.mappers.ExchangeRateMapper;
import org.poo.handlers.mappers.UserMapper;
import org.poo.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        final File directory = new File(CheckerConstants.TESTS_PATH);
        final Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            final File resultFile = new File(String.valueOf(path));
            for (final File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        final var sortedFiles = Arrays.stream(Objects.requireNonNull(directory.listFiles())).
                sorted(Comparator.comparingInt(Main::fileConsumer))
                .toList();

        for (final File file : sortedFiles) {
            final String filepath = CheckerConstants.OUT_PATH + file.getName();
            final File out = new File(filepath);
            final boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    @Getter
    private static List<Commerciant> commerciants;
    @Getter
    private static List<User> users;
    @Getter
    private static CurrencyConverter currencyConverter;
    @Getter
    private static int timestamp;

    /**
     * Action.
     *
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        Utils.resetRandom();
        SplitPaymentCommand.ACTIVE_SPLIT_PAYMENTS.clear();
        NumberOfTransactionsCashback.REDEEMED.clear();

        final ObjectMapper objectMapper = new ObjectMapper();
        final File file = new File(CheckerConstants.TESTS_PATH + filePath1);
        final ObjectInput inputData = objectMapper.readValue(file, ObjectInput.class);

        final ArrayNode output = objectMapper.createArrayNode();

        final CommandInvoker invoker = new CommandInvoker();
        users = UserMapper.mapToUsers(inputData.getUsers());

        commerciants = CommerciantMapper.mapToCommerciant(inputData.getCommerciants());

        final List<ExchangeRate> exchangeRates = ExchangeRateMapper
                .mapToExchangeRates(inputData.getExchangeRates());
        currencyConverter = new CurrencyConverter(exchangeRates);
        final List<Account> accounts = AccountExtractor.extractAccountsFromUsers(users);

        for (final CommandInput command : inputData.getCommands()) {
            final CommandHandler commandInstance = CommandLogicFactory
                    .getCommandLogic(command, users, accounts, currencyConverter);
            if (commandInstance != null) {
                timestamp = command.getTimestamp();
                invoker.addCommand(commandInstance);
                invoker.executeCommands(output);
            }
        }

        final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }

    /**
     * Method used for extracting the test number from the file name.
     *
     * @param file the input file
     * @return the extracted numbers
     */
    public static int fileConsumer(final File file) {
        return Integer.parseInt(
                file.getName()
                        .replaceAll(CheckerConstants.DIGIT_REGEX, CheckerConstants.EMPTY_STR)
        );
    }

    /**
     * Gets user.
     *
     * @param email the email
     * @return the user
     */
    public static User getUser(final String email) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets account.
     *
     * @param iban the iban
     * @return the account
     */
    public static Account getAccount(final String iban) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getAccountIBAN().equals(iban) || account.getAlias().equals(iban)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Gets card.
     *
     * @param user       the user
     * @param cardNumber the card number
     * @return the card
     */
    public static Card getCard(final User user, final String cardNumber) {
        for (final User checkUser : users) {
            for (final Account account : checkUser.getAccounts()) {
                for (final User accountUser : account.getUsers()) {
                    if (accountUser.getEmail().equals(user.getEmail())) {
                        for (final Card card : account.getCards()) {
                            if (card.getCardNumber().equals(cardNumber)) {
                                return card;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets commerciant.
     *
     * @param commerciant the commerciant
     * @return the commerciant
     */
    public static Commerciant getCommerciant(final String commerciant) {
        for (final Commerciant checkCommerciant : commerciants) {
            if (checkCommerciant.getCommerciantIBAN().equals(commerciant)
                    || checkCommerciant.getName().equals(commerciant)) {
                return checkCommerciant;
            }
        }

        return null;
    }
}
