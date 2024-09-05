package main;

import model.Account;
import service.AccountantService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AccountantApp {
    private static AccountantService accountantService = new AccountantService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Account Manager Portal");
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Update Account");
            System.out.println("3. Delete Account");
            System.out.println("4. View Account Details");
            System.out.println("5. View All Accounts");
            System.out.println("6. Deposit Money");
            System.out.println("7. Withdraw Money");
            System.out.println("8. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Customer ID: ");
                        int customerId = scanner.nextInt();
                        System.out.print("Enter Initial Balance: ");
                        double balance = scanner.nextDouble();
                        scanner.nextLine();

                        accountantService.createAccount(new Account(accountNumber, customerId, balance));
                        System.out.println("Account created successfully.");
                        break;

                    case 2:
                        System.out.print("Enter Account Number: ");
                        accountNumber = scanner.nextLine();
                        System.out.print("Enter New Balance: ");
                        balance = scanner.nextDouble();
                        scanner.nextLine();

                        accountantService.updateAccount(new Account(accountNumber, 0, balance));
                        System.out.println("Account updated successfully.");
                        break;

                    case 3:
                        System.out.print("Enter Account Number to Delete: ");
                        accountNumber = scanner.nextLine();
                        accountantService.deleteAccount(accountNumber);
                        System.out.println("Account deleted successfully.");
                        break;

                    case 4:
                        System.out.print("Enter Account Number to View: ");
                        accountNumber = scanner.nextLine();
                        Account account = accountantService.getAccountByNumber(accountNumber);
                        if (account != null) {
                            System.out.println("Username: " + account.getUsername());
                            System.out.println("Customer ID: " + account.getCustomerId());
                            System.out.println("Account Number: " + account.getAccountNumber());
                            System.out.println("Balance: " + account.getBalance());
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 5:
                        List<Account> accounts = accountantService.getAllAccounts();
                        for (Account acc : accounts) {
                            System.out.println("Username: " + acc.getUsername() +
                                    ", Customer ID: " + acc.getCustomerId() +
                                    ", Account Number: " + acc.getAccountNumber() +
                                    ", Balance: " + acc.getBalance());
                        }
                        break;

                    case 6:
                        System.out.print("Enter Account Number: ");
                        accountNumber = scanner.nextLine();
                        System.out.print("Enter Deposit Amount: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine();

                        accountantService.deposit(accountNumber, depositAmount);
                        System.out.println("Money deposited successfully.");
                        break;

                    case 7:
                        System.out.print("Enter Account Number: ");
                        accountNumber = scanner.nextLine();
                        System.out.print("Enter Withdrawal Amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        scanner.nextLine();

                        accountantService.withdraw(accountNumber, withdrawalAmount);
                        System.out.println("Money withdrawn successfully.");
                        break;

                    case 8:
                        System.out.println("Exiting Accountant Portal...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
