package dao;

import exeception.TransactionException;
import model.Transaction;

import java.util.List;

public interface TransactionDao {
    public boolean doTransaction(Transaction t) throws TransactionException;

    public List<Transaction> viewTransactionHistory(int accountId) throws TransactionException;

    public boolean removeTransactionHistory(int accountId) throws TransactionException;
}
