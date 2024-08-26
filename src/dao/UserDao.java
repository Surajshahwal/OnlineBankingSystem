package dao;

import exeception.UserException;
import model.User;

import java.util.List;

public interface UserDao {

    public boolean addUser(User u) throws UserException;

    public User getUserDetailsByUsernameAndPas(String username, String password) throws UserException;

    public List<User> getAllUserDetails() throws UserException;

    public boolean updateUser(User u) throws UserException;

    public boolean deleteUser(int userId) throws UserException;
}
