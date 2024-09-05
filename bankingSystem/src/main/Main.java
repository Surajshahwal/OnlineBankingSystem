package main;

import service.AuthService;
import model.Customer;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static AuthService authService = new AuthService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Online Banking System");
        System.out.println("1. Login");
        System.out.println("2. Register");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            try {
                Customer customer = authService.login(username, password);
                if (customer != null) {
                    System.out.println("Login successful. Welcome, " + customer.getName() + "!");
                    CustomerApp.main(new String[]{});
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (choice == 2) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            try {
                authService.register(new Customer(0, username, password, name));
                System.out.println("Registration successful. Please login.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}
