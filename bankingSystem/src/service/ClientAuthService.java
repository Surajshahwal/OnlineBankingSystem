package service;

import dao.ClientDao;
import model.Customer;

import java.sql.SQLException;

public class ClientAuthService {
    private ClientDao clientDao;

    public ClientAuthService() {
        clientDao = new ClientDao();
    }

    public Customer login(String username, String password) throws SQLException {
        Customer customer = clientDao.getCustomerByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        } else {
            throw new SQLException("Invalid username or password");
        }
    }

    public void register(Customer customer) throws SQLException {
        clientDao.addCustomer(customer);
    }
}
