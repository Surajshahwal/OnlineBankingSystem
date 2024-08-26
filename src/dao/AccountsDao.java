package dao;

import exeception.AccountException;
import model.Account;

import java.util.List;

public interface AccountsDao {
    //create
    public boolean createAccount(Account c) throws AccountException;

    //read
    public Account getAccountDetailsByAccountNumber(String accountNumber) throws AccountException;

    public List<Account> getAllAccountDetails() throws AccountException;

    //update
    public boolean updateAccountDetails(Account u) throws AccountException;

    //delete
    public boolean deleteAccount(int accountId) throws AccountException;
}
