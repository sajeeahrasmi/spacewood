package lk.ijse.spacewood.model;

import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.Log;
import lk.ijse.spacewood.dto.OrderDTO;
import lk.ijse.spacewood.dto.OrderDetailDTO;
import lk.ijse.spacewood.dto.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class StockModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static int getCount(String name) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, props);
        String sql = " select count(*) from log where type = ? and used = ?;";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,name);
        pstm.setString(2,"No");
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static List<Log> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, props);
        String sql = " select * from log ;";

        List<Log> data = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            data.add(new Log(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(8)
            ));
        }
        return data;


    }

    public static List<Log> getSelected(String type) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, props);
        String sql = " select * from log where type = ? ;";

        List<Log> data = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, type);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            data.add(new Log(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(8)
            ));
        }
        return data;
    }

    public static List<String> getIds() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, props);
        String sql = "select log_id from log where used = \"No\";";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static String getType(String logId) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, props);
        String sql = "select type from log where log_id = ?;";


        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, logId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
           return (resultSet.getString(1));
        }
        return null;
    }

    public static boolean addWoodcutDetails(List<Stock> stockList) throws SQLException {
        for (Stock dto : stockList) {
            if(!addDetails(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean addDetails(Stock dto) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, props);
        String sql = "insert into woodcut values (?,?,?,?,?,?,?);";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getWoodcutId());
        pstm.setString(2, dto.getType());
        pstm.setDouble(3, dto.getLength());
        pstm.setDouble(4, dto.getWidth());
        pstm.setDouble(5, dto.getHeight());
        pstm.setInt(6, dto.getQty());
        pstm.setString(7, dto.getLogId());

        return pstm.executeUpdate() > 0;
    }

    public static int getWoodcutCount(String name) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = " select  sum(no_of_pieces) from woodcut where type = ? ;";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static List<Stock> getDetailFromLogId(String logId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = " select * from woodcut where log_id = ? ;";

        List<Stock> data = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, logId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            data.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    public static List<Stock> getDetailFromType(String type) throws SQLException {
        Connection connection =DBConnection.getInstance().getConnection();
        String sql = " select * from woodcut where type = ? ;";

        List<Stock> data = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, type);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            data.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getInt(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    public static boolean updateWoodcutDetailsAgain(List<Stock> stockList) throws SQLException {
        for (Stock dto : stockList) {
            if(!updateDetails(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateDetails(Stock dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = " update woodcut set no_of_pieces = ? where woodcut_id = ? and log_id = ?;";


        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, dto.getQty());
        pstm.setString(2,dto.getWoodcutId());
        pstm.setString(3, dto.getLogId());


        return pstm.executeUpdate() > 0;
    }
}
