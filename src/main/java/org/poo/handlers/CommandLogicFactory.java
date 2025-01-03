package org.poo.handlers;

import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.commands.*;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * The type Command logic factory.
 * It contains the getCommandLogic method.
 * It is used to get the command logic based on the command.
 */
public final class CommandLogicFactory {
    private CommandLogicFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Gets command logic.
     *
     * @param command           the command
     * @param users             the users
     * @param accounts          the accounts
     * @param currencyConverter the currency converter
     * @return the command logic
     */
    public static CommandHandler getCommandLogic(
            final CommandInput command,
            final List<User> users,
            final List<Account> accounts,
            final CurrencyConverter currencyConverter
    ) {
        return switch (command.getCommand()) {
            case "printTransactions" -> new PrintTransactionsCommand(
                    command.getEmail(), command.getTimestamp(), users);
            case "printUsers" -> new PrintUsersCommand(users, command.getTimestamp());
            case "addAccount" -> new AddAccountCommand(
                    command.getEmail(),
                    command.getCurrency(),
                    command.getAccountType(),
                    command.getInterestRate(),
                    command.getTimestamp(),
                    users
            );
            case "addFunds" -> new AddFundsCommand(command.getAccount(),
                    command.getAmount(), command.getTimestamp(), users);
            case "createCard" -> new AddCardsCommand(command.getAccount(),
                    command.getEmail(), false, command.getTimestamp(), users);
            case "createOneTimeCard" -> new AddCardsCommand(command.getAccount(),
                    command.getEmail(), true, command.getTimestamp(), users);
            case "deleteAccount" -> new DeleteAccountCommand(command.getAccount(),
                    command.getTimestamp(), command.getEmail(), users);
            case "deleteCard" -> new DeleteCardCommand(command.getCardNumber(),
                    command.getTimestamp(), users);
            case "payOnline" -> new PayOnlineCommand(
                    command.getCardNumber(),
                    command.getAmount(),
                    command.getCurrency(),
                    command.getTimestamp(),
                    command.getDescription(),
                    command.getCommerciant(),
                    command.getEmail(),
                    users,
                    currencyConverter
            );
            case "sendMoney" -> new SendMoneyCommand(
                    command.getAccount(),
                    command.getAmount(),
                    command.getReceiver(),
                    command.getTimestamp(),
                    command.getDescription(),
                    accounts,
                    currencyConverter,
                    users
            );
            case "setAlias" -> new SetAliasCommand(command.getEmail(),
                    command.getAlias(), command.getAccount(), users);
            case "setMinBalance" -> new SetMinBalanceCommand(command.getAmount(),
                    command.getAccount(), command.getTimestamp(), users);
            case "checkCardStatus" -> new CheckCardStatusCommand(command.getCardNumber(),
                    command.getTimestamp(), users);
            case "splitPayment" -> new SplitPaymentCommand(
                    command.getAccounts(),
                    command.getTimestamp(),
                    command.getCurrency(),
                    command.getAmount(),
                    users,
                    currencyConverter
            );
            case "report" -> new ReportPrintCommand(
                    command.getStartTimestamp(),
                    command.getEndTimestamp(),
                    command.getAccount(),
                    command.getTimestamp(),
                    users
            );
            case "spendingsReport" -> new SpendingReportPrintCommand(
                    command.getStartTimestamp(),
                    command.getEndTimestamp(),
                    command.getAccount(),
                    command.getTimestamp(),
                    users
            );
            case "addInterest" -> new AddInterestCommand(command.getTimestamp(),
                    command.getAccount(), command.getInterestRate(), users);
            case "changeInterestRate" -> new ChangeInterestRateCommand(
                    command.getAccount(),
                    command.getInterestRate(),
                    command.getTimestamp(),
                    users
            );
            default -> null;
        };
    }
}
