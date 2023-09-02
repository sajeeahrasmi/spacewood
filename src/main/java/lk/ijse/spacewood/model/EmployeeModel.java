package lk.ijse.spacewood.model;

import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.Customer;
import lk.ijse.spacewood.dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static String nextEmployeeID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1;";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if(resultSet.next()) {

            return splitOrderId(resultSet.getString(1));

        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String CurrentId) {
        if(CurrentId != null) {
            String[] strings = CurrentId.split("E0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "E00"+id;
        }
        return "E001";
    }

    public static boolean addEmployee(String id, String fName, String lName, String address, String phone, String whatsapp, String nic, String dateJoined) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?,?,?,null);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        statement.setString(2, fName);
        statement.setString(3,nic);
        statement.setString(4, address);
        statement.setString(5,phone);
        statement.setString(6,lName);
        statement.setString(7,whatsapp);
        statement.setString(8,dateJoined);
//(employee_id, first_name, NIC, address, phone, last_name, whatsapp, date)

        return statement.executeUpdate() >0;
    }

    public static List<String> getIds() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select employee_id from employee;";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static List<String> getNames() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select first_name from employee;";
        List<String> names = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            names.add(resultSet.getString(1));
        }
        return names;
    }

    public static Employee searchByName(String name) throws SQLException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee WHERE first_name = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );
        }
        return null;
    }

    public static Employee searchById(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();;
        String sql = "SELECT * FROM employee WHERE employee_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );
        }
        return null;
    }

    public static boolean updateEmployeeDetails(String date, String phone, String whatsapp, String id) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET phone = ?, whatsapp = ?, dateLeft = ? WHERE employee_id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,phone);
        statement.setString(2,whatsapp);
        statement.setString(3,date);
        statement.setString(4,id);
        int i = statement.executeUpdate();
        return i>0;
    }

    public static boolean updateEmployeeDetails(String phone, String whatsapp, String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET phone = ?, whatsapp = ? WHERE employee_id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,phone);
        statement.setString(2,whatsapp);

        statement.setString(3,id);
        int i = statement.executeUpdate();
        return i>0;
    }
}
