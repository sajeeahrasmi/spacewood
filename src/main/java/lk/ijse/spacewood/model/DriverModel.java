package lk.ijse.spacewood.model;

import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.Customer;
import lk.ijse.spacewood.dto.Driver;
import lk.ijse.spacewood.dto.Order;
import lk.ijse.spacewood.dto.OrderDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DriverModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static List<String> getIds() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select driver_id from driver;";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static String nextDriverID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT driver_id FROM driver ORDER BY driver_id DESC LIMIT 1;";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if(resultSet.next()) {

            return splitOrderId(resultSet.getString(1));

        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String CurrentId) {
        if(CurrentId != null) {
            String[] strings = CurrentId.split("D0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "D00"+id;
        }
        return "D001";
    }

    public static boolean addDriver(String id, String name, String address, String phone, String whatsapp, String vehicle) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO driver VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        statement.setString(2, name);
        statement.setString(3,phone);
        statement.setString(4, address);
        statement.setString(5,whatsapp);
        statement.setString(6,vehicle);

        return statement.executeUpdate() >0;
    }

    public static List<String> getNames() throws SQLException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "select driver_name from driver;";
        List<String> names = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            names.add(resultSet.getString(1));
        }
        return names;

    }

    public static Driver searchByName(String name) throws SQLException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM driver WHERE driver_name = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Driver(
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

    public static Driver searchById(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM driver WHERE driver_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Driver(
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

    public static List<OrderDetails> getAll(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orderdetail where driver_id = ?";

        List<OrderDetails> data = new ArrayList<>();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            data.add(new OrderDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
        }
        return data;
    }

    public static boolean updateDriverDetails(String phone, String whatsapp, String id, String vehicle) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE driver SET Phone = ?, whatsapp = ? ,vehicle = ? WHERE driver_id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,phone);
        statement.setString(2,whatsapp);
        statement.setString(3,vehicle);
        statement.setString(4,id);
        int i = statement.executeUpdate();
        return i>0;
    }
}
