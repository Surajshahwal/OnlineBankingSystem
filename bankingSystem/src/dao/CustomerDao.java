package dao;

import model.Customer;
import util.DBConnection;

import java.sql.*;

public class CustomerDao {

    public void addCustomer(Customer customer) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "INSERT INTO customers (username, password, name) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, customer.getUsername());
        stmt.setString(2, customer.getPassword());
        stmt.setString(3, customer.getName());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public Customer getCustomerByUsername(String username) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM customers WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        Customer customer = null;
        if (rs.next()) {
            customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"));
        }
        rs.close();
        stmt.close();
        conn.close();

        return customer;
    }
}
