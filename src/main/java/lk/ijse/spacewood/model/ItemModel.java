package lk.ijse.spacewood.model;

import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.Customer;
import lk.ijse.spacewood.dto.Item;

import java.sql.*;
import java.util.Properties;

public class ItemModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }
    public static Item searchByItem(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE description = ?;";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return null;

    }

    public static Item searchByItemCode(String name) throws SQLException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE Item_code = ?;";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return null;

    }

    public static String nextItemID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Item_code FROM item ORDER BY Item_code DESC LIMIT 1;";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if(resultSet.next()) {

            return splitOrderId(resultSet.getString(1));

        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String CurrentId) {
        if(CurrentId != null) {
            String[] strings = CurrentId.split("I0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "I00"+id;
        }
        return "I001";
    }

    public static boolean addItem(String id, String description, double price) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO item VALUES(?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        statement.setString(2, description);
        statement.setDouble(3,price);


        return statement.executeUpdate() >0;
    }
}
