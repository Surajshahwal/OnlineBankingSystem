package main;

import service.CustomerService;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerApp {
    private static CustomerService customerService = new CustomerService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Customer Portal");
        while (true) {
            System.out.println("1. Transfer Money");
            System.out.println("2. View Transaction History");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Your Account Number: ");
                        String fromAccountNumber = scanner.nextLine();
                        System.out.print("Enter Recipient's Account Number: ");
                        String toAccountNumber = scanner.nextLine();
                        System.out.print("Enter Amount to Transfer: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        customerService.transferMoney(fromAccountNumber, toAccountNumber, amount);
                        System.out.println("Money transferred successfully.");
                        break;

                    case 2:
                        System.out.print("Enter Account Number to View Transaction History: ");
                        String accountNumber = scanner.nextLine();
                        customerService.getTransactionHistory(accountNumber)
                                .forEach(transaction -> System.out.println("Transaction ID: " + transaction.getId() +
                                        ", Amount: " + transaction.getAmount() +
                                        ", Type: " + transaction.getTransactionType() +
                                        ", Date: " + transaction.getDate()));
                        break;

                    case 3:
                        System.out.println("Exiting Customer Portal...");
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
