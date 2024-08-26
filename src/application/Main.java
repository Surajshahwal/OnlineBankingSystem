package application;

import dao.UserDao;
import daoImpl.AccountsDaoImpl;
import daoImpl.TransactionDaoImpl;
import daoImpl.UserDaoImpl;
import exeception.AccountException;
import exeception.TransactionException;
import exeception.UserException;
import model.Transaction;
import model.User;
import service.AccountantService;
import service.UserService;
import utility.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static AccountantService accountantService;
    private static UserService userService;

    private static UserDao userDao;
    private static User loggedUser;

    private static Connection doSimple() throws SQLException {
        Connection con = null;
        try {
            con = Dao.getConnectionFactory().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        try {
            Connection connection = doSimple();
            AccountsDaoImpl accountsDao = new AccountsDaoImpl();
            TransactionDaoImpl transactionDao = new TransactionDaoImpl();
            userDao = new UserDaoImpl();
            accountantService = new AccountantService(accountsDao, transactionDao);
            userService = new UserService(accountsDao, transactionDao);
            //start the application
            runApplication();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void runApplication() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to the Bank Application");
            System.out.println("1. Register new customer");
            System.out.println("2. Login  your account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); //  newline

            switch (choice) {
                case 1:
                    registerAccount(sc);
                    break;
                case 2:
                    if (loginToAccount(sc)) {
                        showMainMenu(sc);
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    private static boolean loginToAccount(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        // Validate login credentials
        try {
            loggedUser = userDao.getUserDetailsByUsernameAndPas(username, password);
            if (loggedUser != null) {
                System.out.println("Login successful! Welcome, " + loggedUser.getUserName());
                return true;
            } else {
                System.out.println("Invalid username or password. Please try again.");
                return false;
            }
        } catch (UserException e) {

            e.printStackTrace();
            return false;
        }

    }

    private static void registerAccount(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();


        // Register the user as a customer
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);
        boolean success = false;
        try {
            success = userDao.addUser(newUser);
        } catch (UserException e) {
            e.printStackTrace();
        }
        if (success) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Failed to register. Username may already be taken.");
        }
    }

    private static void showMainMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Main Menu");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (loggedUser != null) {
                        createAccount(scanner);
                    } else {
                        System.out.println("Only accountants can create accounts.");
                    }
                    break;
                case 2:
                    depositMoney(scanner);
                    break;
                case 3:
                    withdrawMoney(scanner);
                    break;
                case 4:
                    transferMoney(scanner);
                    break;
                case 5:
                    viewTransactionHistory(scanner);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount(Scanner sc) {
        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter account number: ");
        String accountNumber = sc.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = sc.nextDouble();

        boolean success = false;
        try {
            success = accountantService.createAccount(customerId, accountNumber, initialBalance);
            if (!success) {
                System.out.println("Account created successfully!");
            } else {
                System.out.println("Failed to create account. Please try again.");
            }
        } catch (AccountException e) {

            e.printStackTrace();

        }
    }

    private static void depositMoney(Scanner sc) {
        System.out.print("Enter account ID: ");
        String accountId = sc.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        boolean success;
        try {
            success = accountantService.deposit(accountId, amount);
            if (success) {
                System.out.println("Deposit successful!");
            } else {
                System.out.println("Failed to deposit money. Please try again.");
            }
        } catch (AccountException e) {

            e.printStackTrace();
        } catch (TransactionException e) {

            e.printStackTrace();
        }


    }

    private static void withdrawMoney(Scanner sc) {
        System.out.print("Enter account ID: ");
        int accountId = sc.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();

        boolean success = accountantService.withdraw(String.valueOf(accountId), amount);
        if (success) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Failed to withdraw money. Please try again.");
        }
    }

    private static void transferMoney(Scanner sc) {
        System.out.print("Enter source account ID: ");
        int fromAccountId = sc.nextInt();
        System.out.print("Enter destination account No.: ");
        String toAccountNo = sc.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();

        boolean success = userService.transfer(fromAccountId, toAccountNo, amount);
        if (success) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Failed to transfer money. Please try again.");
        }
    }

    private static void viewTransactionHistory(Scanner scanner) {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();

        List<Transaction> transactions = null;
        try {
            transactions = userService.getTransactionHistory(accountId);
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println("Transaction ID: " + transaction.getTransactionId() + ", Amount: " + transaction.getAmount() + ", Type: " + transaction.getTransactionType() + ", Date: " + transaction.getDate());
            }
        }
    }
}

