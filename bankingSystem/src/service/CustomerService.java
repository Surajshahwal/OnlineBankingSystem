package service;

import dao.AccountDao;
import dao.TransactionDao;
import model.Account;
import model.Transaction;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class CustomerService {
    private AccountDao AccountDao;
    private TransactionDao transactionDao;

    public CustomerService() {
        AccountDao = new AccountDao();
        transactionDao = new TransactionDao();
    }

    public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount) throws SQLException {
        Account fromAccount = AccountDao.getAccountByNumber(fromAccountNumber);
        Account toAccount = AccountDao.getAccountByNumber(toAccountNumber);

        if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
            // Deduct from source account
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            AccountDao.updateAccount(fromAccount);

            // Add to destination account
            toAccount.setBalance(toAccount.getBalance() + amount);
            AccountDao.updateAccount(toAccount);

            // Record transactions
            Transaction fromTransaction = new Transaction(0, fromAccountNumber, -amount, "Transfer Out",
                    new Timestamp(System.currentTimeMillis()));
            Transaction toTransaction = new Transaction(0, toAccountNumber, amount, "Transfer In",
                    new Timestamp(System.currentTimeMillis()));

            transactionDao.addTransaction(fromTransaction);
            transactionDao.addTransaction(toTransaction);
        } else {
            throw new SQLException("Transfer failed. Either accounts do not exist or insufficient funds.");
        }
    }

    public List<Transaction> getTransactionHistory(String accountNumber) throws SQLException {
        return transactionDao.getTransactionsByAccount(accountNumber);
    }
}
