package daoImpl;

import dao.UserDao;
import exeception.UserException;
import model.User;
import utility.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class UserDaoImpl implements UserDao {
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
    public boolean addUser(User u) throws UserException {
        String query = "INSERT INTO User (userId, userName, password) VALUES (?, ?, ?, ?)";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, u.getUserId());
            ppst.setString(2, u.getUserName());
            ppst.setString(3, u.getPassword());
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("User added Successfully");
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
    public User getUserDetailsByUsernameAndPas(String username, String password) throws UserException {
        User user = null;
        String query = "SELECT * FROM User WHERE userName = ? AND password = ?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setString(1, username);
            ppst.setString(2, password);
            rs = ppst.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"));
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
        System.out.println(user);
        return user;
    }


    @Override
    public List<User> getAllUserDetails() throws UserException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            rs = ppst.executeQuery(query);
            while (rs.next()) {
                users.add(new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password")));
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
        System.out.println(users);
        return users;
    }

    @Override
    public boolean updateUser(User u) throws UserException {
        String query = "UPDATE User SET userName = ?, password = ? WHERE userId = ?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setString(1, u.getUserName());
            ppst.setString(2, u.getPassword());
            ppst.setInt(3, u.getUserId());
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("User updated successfully ");
            } else {
                System.out.println("Something went wrong");
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
    public boolean deleteUser(int userId) throws UserException {
        String query = "DELETE FROM User WHERE userId=?";
        try {
            con = doSimple();
            ppst = con.prepareStatement(query);
            ppst.setInt(1, userId);
            int ans = ppst.executeUpdate();
            if (ans != 0) {
                System.out.println("User details deleted successfully ");
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
        UserDaoImpl user = new UserDaoImpl();
        try {
           user.updateUser(new User(1, "peter", "suraj@123"));
//            user.addUser(new User(2, "suraj", "aj!23"));
//            user.addUser(new User(3, "raju", "suj23"));
//            user.deleteUser(2);
//            user.getUserDetailsByUsernameAndPas("ksmdin", "ien");

        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }
}
