package service;

import dao.AccountsDao;
import dao.TransactionDao;
import daoImpl.AccountsDaoImpl;
import exeception.TransactionException;
import model.Account;
import model.Transaction;

import java.sql.Timestamp;
import java.util.List;

public class UserService {
    private AccountsDao accountsDao;
    private TransactionDao transactionDao;

    public UserService(AccountsDao accountsDao, TransactionDao transactionDao) {
        super();
        this.accountsDao = accountsDao;
        this.transactionDao = transactionDao;
    }

    // Method to transfer money between accounts
    public boolean transfer(int accountId, String toAccountNumber, double amount) {
        try {
            Account fromAccount = accountsDao.getAccountDetailsByAccountNumber(String.valueOf(accountId));
            Account toAccount = accountsDao.getAccountDetailsByAccountNumber(String.valueOf(toAccountNumber));

            if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
                // Withdraw from the source account
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                accountsDao.updateAccountDetails(fromAccount);
                transactionDao.doTransaction(new Transaction(0, accountId, -amount, toAccountNumber, "Transfer", new Timestamp(System.currentTimeMillis())));

                // Deposit into the destination account
                toAccount.setBalance(toAccount.getBalance() + amount);
                accountsDao.updateAccountDetails(toAccount);
                transactionDao.doTransaction(new Transaction(0, accountId, amount, toAccountNumber, "Transfer", new Timestamp(System.currentTimeMillis())));

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to view transaction history for an account
    public List<Transaction> getTransactionHistory(int accountId) throws TransactionException {
        return transactionDao.viewTransactionHistory(accountId);
    }
}
