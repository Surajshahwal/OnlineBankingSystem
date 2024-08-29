package main;

import model.Account;
import service.AccountManagerService;

import java.sql.SQLException;
import java.util.Scanner;

public class AccountManagerApp {
    private static AccountManagerService accountManagerService = new AccountManagerService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Account Manager Portal");
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Update Account");
            System.out.println("3. Delete Account");
            System.out.println("4. View Account Details");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Customer ID: ");
                        int customerId = scanner.nextInt();
                        System.out.print("Enter Balance: ");
                        double balance = scanner.nextDouble();
                        scanner.nextLine();

                        Account newAccount = new Account(accountNumber, customerId, balance);
                        accountManagerService.createAccount(newAccount);
                        System.out.println("Account created successfully.");
                        break;

                    case 2:
                        System.out.print("Enter Account Number to Update: ");
                        String updateAccountNumber = scanner.nextLine();
                        System.out.print("Enter New Balance: ");
                        double newBalance = scanner.nextDouble();
                        scanner.nextLine();

                        Account accountToUpdate = accountManagerService.getAccountByNumber(updateAccountNumber);
                        if (accountToUpdate != null) {
                            accountToUpdate.setBalance(newBalance);
                            accountManagerService.updateAccount(accountToUpdate);
                            System.out.println("Account updated successfully.");
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Account Number to Delete: ");
                        String deleteAccountNumber = scanner.nextLine();
                        accountManagerService.deleteAccount(deleteAccountNumber);
                        System.out.println("Account deleted successfully.");
                        break;

                    case 4:
                        System.out.print("Enter Account Number to View: ");
                        String viewAccountNumber = scanner.nextLine();
                        Account account = accountManagerService.getAccountByNumber(viewAccountNumber);
                        if (account != null) {
                            System.out.println("Account Number: " + account.getAccountNumber());
                            System.out.println("Customer ID: " + account.getCustomerId());
                            System.out.println("Balance: " + account.getBalance());
                            System.out.println("Username: " + account.getUsername());
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting Account Manager Portal...");
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
