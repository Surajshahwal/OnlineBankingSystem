package service;

import dao.AccountDao;
import model.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountManagerService {
    private AccountDao AccountDao;

    public AccountManagerService() {
        AccountDao = new AccountDao();
    }

    public void createAccount(Account account) throws SQLException {
        AccountDao.addAccount(account);
    }

    public void updateAccount(Account account) throws SQLException {
        AccountDao.updateAccount(account);
    }

    public void deleteAccount(String accountNumber) throws SQLException {
        AccountDao.deleteAccount(accountNumber);
    }

    public Account getAccountByNumber(String accountNumber) throws SQLException {
        return AccountDao.getAccountByNumber(accountNumber);
    }

    public List<Account> getAllAccounts() throws SQLException {
        return AccountDao.getAllAccounts();
    }

    public void deposit(String accountNumber, double amount) throws SQLException {
        Account account = AccountDao.getAccountByNumber(accountNumber);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            AccountDao.updateAccount(account);
        }
    }

    public void withdraw(String accountNumber, double amount) throws SQLException {
        Account account = AccountDao.getAccountByNumber(accountNumber);
        if (account != null && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            AccountDao.updateAccount(account);
        }
    }
}
