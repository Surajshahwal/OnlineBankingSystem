package model;

public class Account {
    //private variables
    private int account_Id;
    private int customer_Id;

    private String accountNumber;

    private double balance;

    //constructors

    public Account(int account_Id, int customer_Id, String accountNumber, double balance) {
        this.account_Id = account_Id;
        this.customer_Id = customer_Id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account() {
    }
    //getter and setter

    public int getAccount_Id() {
        return account_Id;
    }

    public void setAccount_Id(int account_Id) {
        this.account_Id = account_Id;
    }

    public int getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(int customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // toString method
    @Override
    public String toString() {
        return "Account{" +
                "account_Id=" + account_Id +
                ", customer_Id=" + customer_Id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}