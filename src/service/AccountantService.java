package service;

import com.mysql.cj.util.StringInspector;
import dao.AccountsDao;
import dao.TransactionDao;
import exeception.AccountException;
import exeception.TransactionException;
import model.Account;
import model.Transaction;

import java.sql.Timestamp;


public class AccountantService {

    private AccountsDao accountsDao;
    private TransactionDao transactionDao;

    public AccountantService(AccountsDao accountsDao, TransactionDao transactionDao) {
        this.accountsDao = accountsDao;
        this.transactionDao = transactionDao;
    }

    //method to create a new account
    public boolean createAccount(int customerId, String accountNumber, double initialBalance) throws AccountException {
        Account account = null;
        account = new Account(0, customerId, accountNumber, initialBalance);
        return accountsDao.createAccount(account);
    }

    //method to deposit money into account
    public boolean deposit(String accountNumber, double amount) throws AccountException, TransactionException {
        Account account = accountsDao.getAccountDetailsByAccountNumber(String.valueOf(accountNumber));
        if (account != null) {
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            boolean accountUpdated = accountsDao.updateAccountDetails(account);

            if (accountUpdated) {
                Transaction transaction = new Transaction(0, 0, -amount, accountNumber, "Deposit", new Timestamp(System.currentTimeMillis()));
                return transactionDao.doTransaction(transaction);
            }
        }

        return false;
    }

    // Method to withdraw money from an account
    public boolean withdraw(String accountNumber, double amount) {
        try {
            Account account = accountsDao.getAccountDetailsByAccountNumber(String.valueOf(accountNumber));
            if (account != null && account.getBalance() >= amount) {
                double newBalance = account.getBalance() - amount;
                account.setBalance(newBalance);
                boolean accountUpdated = accountsDao.updateAccountDetails(account);

                if (accountUpdated) {
                    Transaction transaction = new Transaction(0, 0, -amount, accountNumber, "Withdrawal", new Timestamp(System.currentTimeMillis()));
                    return transactionDao.doTransaction(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to get account details by account number
    public Account getAccountByAccountNumber(String accountNumber) throws AccountException {
        return accountsDao.getAccountDetailsByAccountNumber(accountNumber);
    }
}


