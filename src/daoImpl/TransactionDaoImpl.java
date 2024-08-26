package daoImpl;

import dao.TransactionDao;
import exeception.TransactionException;
import model.Transaction;
import utility.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyPermission;

public class TransactionDaoImpl implements TransactionDao {

    Connection con = null;
    PreparedStatement ppst = null;
    ResultSet rs = null;

    private Connection doSimple() throws SQLException {
        Connection connection = null;
        try {
            connection = Dao.getConnectionFactory().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public boolean doTransaction(Transaction t) throws TransactionException {
        String query = "DELETE FROM Account WHERE account_Id=?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, t.getTransactionId());
            ppst.setInt(2, t.getAccountId());
            ppst.setDouble(3, t.getAmount());
            ppst.setString(4, t.getAccountNumber());
            ppst.setString(5, t.getTransactionType());
            ppst.setTimestamp(6, t.getDate());
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("Transaction done Successfully");
            } else {
                System.out.println("something went wrong");
            }
            return ans > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ppst != null) {
                    ppst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Transaction> viewTransactionHistory(int accountId) throws TransactionException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transaction WHERE account_Id = ?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, accountId);
            rs = ppst.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(

                        rs.getInt("transactionId"), rs.getInt("accountId"), rs.getDouble("amount"), rs.getString("accountNumber"), rs.getString("transactionType"), rs.getTimestamp("date")


                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ppst != null) {
                    ppst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        transactions.forEach(System.out::println);
        return transactions;

    }

    @Override
    public boolean removeTransactionHistory(int accountId) throws TransactionException {
        String query = "DELETE FROM Account WHERE account_Id=?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, accountId);
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("TransactionHistory " + accountId + "deleted successfully");

            } else {
                System.out.println("something went wrong ");

            }
            return ans > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ppst != null) {
                    ppst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;

    }

    public static void main(String[] args) {
        TransactionDaoImpl t = new TransactionDaoImpl();
        try {
//            t.doTransaction(new Transaction(1, 3, 3000.0, "25632466", "Deposit", new Timestamp(System.currentTimeMillis())));
//            t.doTransaction(new Transaction(2, 4, 3600.0, "72330200010653", "Withdrawal", new Timestamp(System.currentTimeMillis())))
            t.viewTransactionHistory(1);

        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}