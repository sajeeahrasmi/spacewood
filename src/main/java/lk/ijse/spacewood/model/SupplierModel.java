package lk.ijse.spacewood.model;

import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SupplierModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static String nextSupplierID() throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT sup_id FROM supplier ORDER BY sup_id DESC LIMIT 1;";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if(resultSet.next()) {

            return splitOrderId(resultSet.getString(1));

        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String CurrentId) {

        if(CurrentId != null) {
            String[] strings = CurrentId.split("S0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "S00"+id;
        }
        return "S001";
    }

    public static boolean addSupplier(String id, String fName, String lName, String address, String phone, String whatsapp) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        statement.setString(2, fName);
        statement.setString(3,address);
        statement.setString(4, phone);
        statement.setString(5,lName);
        statement.setString(6,whatsapp);

        return statement.executeUpdate() >0;
    }

    public static List<String> getIds() throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select sup_id from supplier;";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static List<String> getNames() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select First_name from supplier;";
        List<String> names = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            names.add(resultSet.getString(1));
        }
        return names;
    }

    public static Supplier searchById(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier WHERE sup_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Supplier(
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

    public static Supplier searchByName(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier WHERE First_name = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Supplier(
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

    public static boolean updateSupplierDetails(String phone, String whatsapp, String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE supplier SET phone = ?, whatsapp = ? WHERE sup_id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,phone);
        statement.setString(2,whatsapp);
        statement.setString(3,id);
        int i = statement.executeUpdate();
        return i>0;
    }

    public static boolean updateLogTable(List<Log> logList) throws SQLException {
        for (Log dto : logList) {
            if(!updateLogTableDetail(dto)) {
                return false;
            }
        }
        return true;


    }

    private static boolean updateLogTableDetail(Log log) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into log values (?,?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, log.getLogId());
        statement.setString(2, log.getType());
        statement.setString(3, log.getSupId());
        statement.setString(4, log.getLocation());
        statement.setString(5,log.getPermitNo());
        statement.setString(6,log.getDescription());
        statement.setString(7, log.getDate());
        int i = statement.executeUpdate();
        return i>0;
    }

    public static List<Log> getAll(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM log where sup_id = ?";

        List<Log> data = new ArrayList<>();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();


        while (resultSet.next()) {
            data.add(new Log(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }
}
