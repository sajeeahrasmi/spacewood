package lk.ijse.spacewood.model;

import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.OrderDTO;
import lk.ijse.spacewood.dto.OrderDetailDTO;
import lk.ijse.spacewood.dto.OrderDetails;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class OrderModel {
    private final static String URL ="jdbc:mysql://localhost:3306/Spacewood";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }
    public static String nextOrderID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Order_id FROM orders ORDER BY Order_id DESC LIMIT 1;";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if(resultSet.next()) {

            return splitOrderId(resultSet.getString(1));

        }
        return splitOrderId(null);

    }
    private static String splitOrderId(String CurrentId) {
        if(CurrentId != null) {
            String[] strings = CurrentId.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "O0"+id;
        }
        return "O001";
    }

    public static List<String> getCodes() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select Item_code from item;";
        List<String> codes = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static List<String> getItemNames() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select description from item;";
        List<String> names = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            names.add(resultSet.getString(1));
        }
        return names;
    }

    public static List<String> getIds() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select Order_id from orders;";
        List<String> ids = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;

    }

    public static boolean updateOrderTable(String customerID, String orderID, String date, String totalCost) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into orders values(?,?,?,?);";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderID);
        pstm.setString(2, customerID);
        pstm.setString(3, String.valueOf(date));
        pstm.setDouble(4, Double.parseDouble(totalCost));

        return pstm.executeUpdate() > 0;

    }

    public static boolean updateOrderDetails(String orderID, List<OrderDTO> orderDetailsList) throws SQLException {
        for (OrderDTO dto : orderDetailsList) {
            if(!updateQty(dto,orderID)) {
                return false;
            }
        }
        return true;

    }

    private static boolean updateQty(OrderDTO dto, String orderID) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, props);
        String sql = "insert into orderDetail (order_id, item_code, qtyOrdered) values (?,?,?);";
       //INSERT INTO orderDetails()
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(2, dto.getCode());
        pstm.setInt(3, dto.getQty());
        pstm.setString(1, orderID);

        return pstm.executeUpdate() > 0;
    }

    public static String loadCustomerId(String orderId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = " select Customer_id from orders where Order_id = ?;";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        String getCustomerId = "";
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            getCustomerId = resultSet.getString(1);
            return getCustomerId;
        }
        return getCustomerId;

    }

    public static List<String> getCodesOfOrder(String orderId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select item_code from orderDetail where order_id = ?;";
        List<String> codes = new ArrayList<>();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }


    public static List<OrderDetails> getAll(String orderId) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orderdetail where Order_id = ?";

        List<OrderDetails> data = new ArrayList<>();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
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

    public static int getOrderedItem(String orderId, String itemCode) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = " select qtyOrdered from orderDetail where order_id = ? and item_code = ?;";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, itemCode);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static boolean updateOrderDetailAgain(String orderId, List<OrderDetailDTO> orderDetailDTOList) throws SQLException {

        for (OrderDetailDTO dto : orderDetailDTOList) {
            if(!updateDetails(dto,orderId)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateDetails(OrderDetailDTO dto, String orderId) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, props);
        String sql = " update orderDetail set qtyDelivered = ?, driver_id = ?, location = ?, date = ? where order_id = ? and item_code = ?;";


        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, dto.getQty());
        pstm.setString(2,dto.getDriverId());
        pstm.setString(3, dto.getLocation());
        pstm.setString(4,dto.getDate());
        pstm.setString(5,orderId);
        pstm.setString(6,dto.getItemCode());

        return pstm.executeUpdate() > 0;

    }

    public static boolean updateBothTables(String customerID, String orderID, String date, String totalCost, List<OrderDTO> orderDTOList) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, props);

            connection.setAutoCommit(false);

            boolean isOrderTableUpdated = OrderModel.updateOrderTable(customerID, orderID, date, totalCost);
            if (isOrderTableUpdated) {
                boolean isOrderDetailUpdated = OrderModel.updateOrderDetails(orderID, orderDTOList);
                if (isOrderDetailUpdated) {
                        connection.commit();
                        return true;
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            System.out.println("finally");
            connection.setAutoCommit(true);
        }

        /*boolean isOrderTableUpdated = OrderModel.updateOrderTable(customerID, orderID, date, totalCost);
        if (isOrderTableUpdated) {
            boolean isOrderDetailUpdated = OrderModel.updateOrderDetails(orderID, orderDTOList);
            if (isOrderDetailUpdated) {
               return true;
            } else {
               return false;
            }
        }
        return false;*/
    }
}
