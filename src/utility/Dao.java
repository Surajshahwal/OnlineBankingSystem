package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
    //singleton class
    String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/onlineBankingSystem";
    private static final String username = "root";
    private static final String password = "Suraj@123";

    private Dao() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static Dao connectionFactory=null;
    public static Dao getConnectionFactory(){
        if (connectionFactory==null){
            connectionFactory=new Dao();
        }
        return connectionFactory;
    }
    //method to get connection from this singleton class

    public static Connection getConnection() throws SQLException {
       Connection con=DriverManager.getConnection(url,username,password);
       return con;
    }

}