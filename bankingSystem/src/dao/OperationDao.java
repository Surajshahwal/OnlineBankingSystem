package dao;

import model.Transaction;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDao {

    public void addTransaction(Transaction transaction) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "INSERT INTO transactions (account_number, amount, transaction_type, date) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, transaction.getAccountNumber());
        stmt.setDouble(2, transaction.getAmount());
        stmt.setString(3, transaction.getTransactionType());
        stmt.setTimestamp(4, transaction.getDate());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM transactions WHERE account_number = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, accountNumber);
        ResultSet rs = stmt.executeQuery();

        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            transactions.add(new Transaction(
                    rs.getInt("id"),
                    rs.getString("account_number"),
                    rs.getDouble("amount"),
                    rs.getString("transaction_type"),
                    rs.getTimestamp("date")));
        }
        rs.close();
        stmt.close();
        conn.close();

        return transactions;
    }
}
