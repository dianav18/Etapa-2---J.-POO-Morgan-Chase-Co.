package org.poo.handlers;

import org.poo.bankInput.Account;
import org.poo.bankInput.User;
import org.poo.commands.AcceptSplitPaymentCommand;
import org.poo.commands.AddAccountCommand;
import org.poo.commands.AddCardsCommand;
import org.poo.commands.AddFundsCommand;
import org.poo.commands.AddInterestCommand;
import org.poo.commands.AddNewBusinessAssociateCommand;
import org.poo.commands.BusinessReportCommand;
import org.poo.commands.CashWithdrawalCommand;
import org.poo.commands.ChangeDepositLimitCommand;
import org.poo.commands.ChangeInterestRateCommand;
import org.poo.commands.ChangeSpendingLimitCommand;
import org.poo.commands.CheckCardStatusCommand;
import org.poo.commands.DeleteAccountCommand;
import org.poo.commands.DeleteCardCommand;
import org.poo.commands.PayOnlineCommand;
import org.poo.commands.PrintTransactionsCommand;
import org.poo.commands.PrintUsersCommand;
import org.poo.commands.RejectSplitPaymentCommand;
import org.poo.commands.ReportPrintCommand;
import org.poo.commands.SendMoneyCommand;
import org.poo.commands.SetAliasCommand;
import org.poo.commands.SetMinBalanceCommand;
import org.poo.commands.SpendingReportPrintCommand;
import org.poo.commands.SplitPaymentCommand;
import org.poo.commands.UpgradePlanCommand;
import org.poo.commands.WithdrawSavingsCommand;
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
            case "addFunds" -> new AddFundsCommand(
                    command.getEmail(),
                    command.getAccount(),
                    command.getAmount(),
                    command.getTimestamp()
            );
            case "createCard" -> new AddCardsCommand(command.getAccount(),
                    command.getEmail(), false, command.getTimestamp(), users);
            case "createOneTimeCard" -> new AddCardsCommand(command.getAccount(),
                    command.getEmail(), true, command.getTimestamp(), users);
            case "deleteAccount" -> new DeleteAccountCommand(command.getAccount(),
                    command.getTimestamp(), command.getEmail(), users);
            case "deleteCard" -> new DeleteCardCommand(
                    command.getCardNumber(),
                    command.getTimestamp()
            );
            case "payOnline" -> new PayOnlineCommand(
                    command.getCardNumber(),
                    command.getAmount(),
                    command.getCurrency(),
                    command.getTimestamp(),
                    command.getDescription(),
                    command.getCommerciant(),
                    command.getEmail()
            );
            case "sendMoney" -> new SendMoneyCommand(
                    command.getAccount(),
                    command.getReceiver(),
                    command.getAmount(),
                    command.getTimestamp(),
                    command.getDescription()
            );
            case "setAlias" -> new SetAliasCommand(command.getEmail(),
                    command.getAlias(), command.getAccount(), users);
            case "setMinBalance" -> new SetMinBalanceCommand(command.getAmount(),
                    command.getAccount(), command.getTimestamp(), users);
            case "checkCardStatus" -> new CheckCardStatusCommand(command.getCardNumber(),
                    command.getTimestamp(), users);
            case "splitPayment" -> new SplitPaymentCommand(
                    command.getTimestamp(),
                    command.getCurrency(),
                    command.getAmount(),
                    command.getSplitPaymentType(),
                    command.getAccounts(),
                    command.getAmountForUsers()
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
            case "withdrawSavings" -> new WithdrawSavingsCommand(
                    command.getAccount(),
                    command.getAmount(),
                    command.getCurrency(),
                    command.getTimestamp()
            );
            case "upgradePlan" -> new UpgradePlanCommand(
                    command.getNewPlanType(),
                    command.getAccount(),
                    command.getTimestamp()
            );
            case "cashWithdrawal" -> new CashWithdrawalCommand(
                    command.getCardNumber(),
                    command.getAmount(),
                    command.getEmail(),
                    command.getTimestamp()
            );
            case "acceptSplitPayment" -> new AcceptSplitPaymentCommand(
                    command.getTimestamp(),
                    command.getEmail(),
                    command.getSplitPaymentType()
            );
            case "addNewBusinessAssociate" -> new AddNewBusinessAssociateCommand(
                    command.getTimestamp(),
                    command.getAccount(),
                    command.getEmail(),
                    command.getRole()
            );
            case "businessReport" -> new BusinessReportCommand(
                    command.getStartTimestamp(),
                    command.getEndTimestamp(),
                    command.getAccount(),
                    command.getType(),
                    command.getTimestamp()
            );
            case "changeSpendingLimit" -> new ChangeSpendingLimitCommand(
                    command.getEmail(),
                    command.getAccount(),
                    command.getAmount(),
                    command.getTimestamp()
            );
            case "changeDepositLimit" -> new ChangeDepositLimitCommand(
                    command.getEmail(),
                    command.getAccount(),
                    command.getAmount(),
                    command.getTimestamp()
            );
            case "rejectSplitPayment" -> new RejectSplitPaymentCommand(
                    command.getTimestamp(),
                    command.getEmail(),
                    command.getSplitPaymentType()
            );
            default -> {
                yield null;
            }
        };
    }
}
