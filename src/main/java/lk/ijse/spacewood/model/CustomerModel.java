package lk.ijse.spacewood.model;

import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.Customer;
import lk.ijse.spacewood.dto.Order;
import lk.ijse.spacewood.dto.OrderDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomerModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }


    public static String nextCustomerID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT id FROM Customer ORDER BY id DESC LIMIT 1;";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if(resultSet.next()) {

          return splitOrderId(resultSet.getString(1));

        }
      return splitOrderId(null);


    }

    private static String splitOrderId(String CurrentId) {
        if(CurrentId != null) {
            String[] strings = CurrentId.split("C0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "C00"+id;
        }
        return "C001";
    }

    public static boolean addCustomer(String id, String fName, String lName, String address, String phone, String whatsapp) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Customer VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fName);
        statement.setString(2, address);
        statement.setString(3,phone);
        statement.setString(4, id);
        statement.setString(5,lName);
        statement.setString(6,whatsapp);

        return statement.executeUpdate() >0;

    }

    public static List<String> getIds() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select id from customer;";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;

    }

    public static List<String> getNames() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select First_name from customer;";
        List<String> names = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            names.add(resultSet.getString(1));
        }
        return names;
    }

    public static Customer searchById(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer WHERE id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;

    }

    public static Customer searchByName(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer WHERE First_name = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    public static boolean updateCustomerDetails(String phone, String whatsapp, String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE customer SET Phone = ?, Whatsapp = ? WHERE id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,phone);
        statement.setString(2,whatsapp);
        statement.setString(3,id);
        int i = statement.executeUpdate();
        return i>0;

    }

    public static boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM Customer WHERE id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,id);
        int i = statement.executeUpdate();
        return i>0;

    }

    public static List<Order> getAll(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orders where Customer_id = ?";

        List<Order> data = new ArrayList<>();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            data.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return data;

    }

    public static boolean checkCustomerOrders(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Order_id FROM orders where Customer_id = ?";


        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();


        if (resultSet.next()) {
           return true;
        }
        return false;
    }
}
