package model;

public class    Account {
//creating the private variables
    private String accountNumber;
    private int customerId;
    private double balance;
    private String username;

    //creating constructor
    public Account(String accountNumber, int customerId, double balance, String username) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.username = username;
    }

    public Account(String accountNumber, int customerId, double balance) {
        // Provide default or empty username
        this(accountNumber, customerId, balance, "");
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
