package service;

import dao.CustomerDao;
import model.Customer;

import java.sql.SQLException;

public class AuthService {
    private CustomerDao customerDao;

    public AuthService() {
        customerDao = new CustomerDao();
    }

    public Customer login(String username, String password) throws SQLException {
        Customer customer = customerDao.getCustomerByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        } else {
            throw new SQLException("Invalid username or password");
        }
    }

    public void register(Customer customer) throws SQLException {
        customerDao.addCustomer(customer);
    }
}
