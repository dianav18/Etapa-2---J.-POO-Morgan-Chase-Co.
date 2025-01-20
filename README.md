# Bank Management System

## Overview 
This Java project implements various functionalities related to a banking system, utilizing several
design patterns to encourage code reusability and a well-defined architecture.

## Design Patterns Used

### 1. Singleton

The Singleton pattern ensures that a class has a single instance throughout the application's
lifecycle.

**Classes implementing Singleton:**

- `Account`
- `User`
- `Checker`
- `Utils`

### 2. Template Method

Template Method defines the general structure of an algorithm in a method of a parent class,
allowing derived classes to implement specific steps.

**Classes implementing Template Method:**

- `BusinessAccount`
- `ClassicAccount`
- `SavingsAccount`

### 3. Command

The Command pattern encapsulates requests as objects, enabling flexible and modular command usage.

**Classes implementing Command:**

- `AcceptSplitPaymentCommand`
- `AddAccountCommand`
- `SendMoneyCommand`
- And many others in the `commands` package.

### 4. Factory Method

The Factory Method pattern delegates object creation in a flexible way.

**Classes implementing Factory Method:**

- `Checker`
- `CommandLogicFactory`

### 5. Visitor

The Visitor pattern separates algorithms from the structure of objects, allowing the addition of
new operations without modifying the existing structure.

**Classes implementing Visitor:**

- `TransactionVisitor`
- `BusinessTransactionPrinter`
## Project Structure

The project is organized into several packages to separate application logic:

- **bankInput:** Core classes for accounts, users, and related logic.
- **commands:** Command implementations using the Command pattern.
- **transactions:** Classes modeling transactions and their logic.
- **handlers:** Classes for handling commands and currency conversions.
- **utils:** Utility classes for common functionalities.

## Key Features

- Management of multiple account types, including classic, savings, and business accounts.
- Support for various transactions, such as deposits, withdrawals, transfers, and online payments.
- Implementation of an extensible and modular architecture using design patterns.
- Detailed financial reports for users and merchants.
- Real-time currency conversion using predefined exchange rates.

## Bank Flow

1. **User Creation:**
    - Users can create accounts using classes like `AddAccountCommand`.
2. **Adding Funds:**
    - Funds can be added to accounts using `AddFundsCommand`.
3. **Transactions:**
    - Users can perform transactions between accounts or to merchants using specific commands.
4. **Generating Reports:**
    - Expense and transaction reports can be generated using commands like
    - `SpendingReportPrintCommand`.
5. **Card Management:**
    - Cards associated with accounts can be created, deleted, or configured through
    - dedicated commands.

## Conclusion

This project effectively utilizes multiple design patterns to create a modular and extensible
architecture. Their use facilitates the addition of new features and the maintenance of
the codebase.

