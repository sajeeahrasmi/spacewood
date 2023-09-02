package lk.ijse.spacewood.model;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Properties;

public class LoginModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";

    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }


    public static boolean validLoginDetail(String user, String pass) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM login ORDER BY Username ASC LIMIT 1;";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        String username = "";
        String password = "";
        if(resultSet.next()){
         username = resultSet.getString(1);
        password = resultSet.getString(2);}
        if(user.equalsIgnoreCase(username) & pass.equalsIgnoreCase(password)){
            return true;
        }
        return false;

    }
}
