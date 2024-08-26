package model;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction {


    //private variables
    private int transactionId;
    private int accountId;
    private double amount;
    private String accountNumber;

    private String TransactionType; //deposit or withdrawal

    private Timestamp date;
    //constructor

    public Transaction(int transactionId, int accountId, double amount, String accountNumber, String transactionType, Timestamp date) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.accountNumber = accountNumber;
        TransactionType = transactionType;
        this.date = date;
    }

    public Transaction() {
    }


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", accountNumber='" + accountNumber + '\'' +
                ", TransactionType='" + TransactionType + '\'' +
                ", date=" + date +
                '}';
    }
}