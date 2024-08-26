package daoImpl;

import dao.AccountsDao;
import exeception.AccountException;
import model.Account;
import utility.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDaoImpl implements AccountsDao {
    Connection con = null;
    PreparedStatement ppst = null;
    ResultSet rs = null;

    private Connection doSimple() {
        Connection connection = null;
        try {
            connection = Dao.getConnectionFactory().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    @Override
    public boolean createAccount(Account account) throws AccountException {

        String query = "INSERT INTO Account (account_Id, customer_Id, accountNumber, balance) VALUES (?, ?, ?, ?)";

        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, account.getAccount_Id());
            ppst.setInt(2, account.getCustomer_Id());
            ppst.setString(3, account.getAccountNumber());
            ;
            ppst.setDouble(4, account.getBalance());
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("Account Created Successfully");
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
    public Account getAccountDetailsByAccountNumber(String accountNumber) throws AccountException {
        ArrayList<Account> listOfAcc = new ArrayList<>();
        String query = "SELECT * FROM Account WHERE accountNumber = ?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setString(1, accountNumber);
            rs = ppst.executeQuery();
            if (rs.next()) {
                listOfAcc.add(new Account(rs.getInt("account_Id"), rs.getInt("customer_Id"), rs.getString("accountNumber"), rs.getDouble("balance")

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
        System.out.println(listOfAcc);
        return null;
    }

    @Override
    public List<Account> getAllAccountDetails() throws AccountException {
        List<Account> listOfAccounts = new ArrayList<>();
        String query = "SELECT * FROM Account";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            rs = ppst.executeQuery();
            while (rs.next()) {
                listOfAccounts.add(new Account(rs.getInt("account_Id"), rs.getInt("customer_Id"), rs.getString("accountNumber"), rs.getDouble("balance")));

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
        listOfAccounts.forEach(System.out::println);
        return listOfAccounts;
    }

    @Override
    public boolean updateAccountDetails(Account u) throws AccountException {
        String query = "UPDATE Accounts SET customer_Id=?, accountNumber = ?, balance = ? WHERE account_Id = ?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, u.getCustomer_Id());
            ppst.setString(2, u.getAccountNumber());
            ppst.setDouble(3, u.getBalance());
            ppst.setInt(4, u.getAccount_Id());
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("Account update successfully");
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
    public boolean deleteAccount(int accountId) throws AccountException {
        String query = "DELETE FROM Accounts WHERE account_Id=?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, accountId);
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("Account with accountId" + accountId + "deleted successfully");

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

    public static void main(String[] args) {
        AccountsDaoImpl a = new AccountsDaoImpl();
        try {
            a.createAccount(new Account(1, 3, "001", 5000.0));
            a.createAccount(new Account(3, 1, "003", 4000.0));
            a.updateAccountDetails(new Account(2, 3, "00002", 7000.0));
            a.deleteAccount(2);
            a.getAccountDetailsByAccountNumber("003");

            //
        } catch (AccountException e) {
            e.printStackTrace();
        }
    }


}
