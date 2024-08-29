package dao;

import model.Account;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    public void addAccount(Account account) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "INSERT INTO accounts (account_number, customer_id, balance) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, account.getAccountNumber());
        stmt.setInt(2, account.getCustomerId());
        stmt.setDouble(3, account.getBalance());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void updateAccount(Account account) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDouble(1, account.getBalance());
        stmt.setString(2, account.getAccountNumber());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public void deleteAccount(String accountNumber) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "DELETE FROM accounts WHERE account_number = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, accountNumber);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public Account getAccountByNumber(String accountNumber) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT a.account_number, a.customer_id, a.balance, c.username FROM accounts a " +
                "JOIN customers c ON a.customer_id = c.id WHERE a.account_number = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, accountNumber);
        ResultSet rs = stmt.executeQuery();

        Account account = null;
        if (rs.next()) {
            account = new Account(
                    rs.getString("account_number"),
                    rs.getInt("customer_id"),
                    rs.getDouble("balance"),
                    rs.getString("username"));
        }
        rs.close();
        stmt.close();
        conn.close();

        return account;
    }

    public List<Account> getAllAccounts() throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT a.account_number, a.customer_id, a.balance, c.username FROM accounts a " +
                "JOIN customers c ON a.customer_id = c.id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        List<Account> accounts = new ArrayList<>();
        while (rs.next()) {
            accounts.add(new Account(
                    rs.getString("account_number"),
                    rs.getInt("customer_id"),
                    rs.getDouble("balance"),
                    rs.getString("username")));
        }
        rs.close();
        stmt.close();
        conn.close();

        return accounts;
    }
}
