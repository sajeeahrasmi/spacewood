package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.ijse.spacewood.dto.OrderDTO;
import lk.ijse.spacewood.dto.OrderDetailDTO;
import lk.ijse.spacewood.dto.OrderDetails;
import lk.ijse.spacewood.dto.tm.AddOrderTM;
import lk.ijse.spacewood.dto.tm.ViewOrderTM;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.DriverModel;
import lk.ijse.spacewood.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class CustomerViewOrderFormController implements Initializable {

    public Label lblCustId;
    public JFXComboBox cmbOrderId;
    public JFXComboBox cmbItemCode;
    public JFXComboBox cmbDriverId;
    public JFXTextField txtQty;
    public JFXTextField txtDate;
    public TableView tblOrderDetail;
    public TableColumn colCode;
    public TableColumn colQty;
    public TableColumn colDriver;
    public TableColumn colDate;
    public TableColumn colLocation;
    public JFXTextField txtLocation;
    public TableColumn colItemNumbers;
    public JFXButton btnSave;
    String orderId;
    int orderedItem;
    private ObservableList<ViewOrderTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadOrderId();
        loadItemCode();
        loadDriverId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDriver.setCellValueFactory(new PropertyValueFactory<>("driver"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colItemNumbers.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));

    }

    private void loadDriverId() {
        try {
            List<String> ids = DriverModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbDriverId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadItemCode() {
        try {
            List<String> codes = OrderModel.getCodes();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String code : codes) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadOrderId() {
        try {
            List<String> ids = OrderModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbOrderId.setItems(obList);
           // cmbOrderId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if(check) {
            String itemCode = (String) cmbItemCode.getValue();
            String driverId = (String) cmbDriverId.getValue();
            int qtyDeliver = Integer.parseInt(txtQty.getText());
            String date = txtDate.getText();
            String location = txtLocation.getText();
            int i = 0;
            if (!obList.isEmpty()) {
                for (i = 0; i < tblOrderDetail.getItems().size(); i++) {
                    if (colCode.getCellData(i).equals(itemCode)) {

                        obList.get(i).setItemNumber((Integer) colItemNumbers.getCellData(i));
                        obList.get(i).setQty(qtyDeliver);
                        obList.get(i).setDriver(driverId);
                        obList.get(i).setDate(date);
                        obList.get(i).setLocation(location);

                        tblOrderDetail.refresh();

                        return;
                    }
                }
            }

            ViewOrderTM tm = new ViewOrderTM(itemCode, orderedItem, driverId, date, location, qtyDeliver);

            obList.add(tm);
            tblOrderDetail.setItems(obList);


            //txtQty.setText("");

        }

    }

    private boolean checkValidation() {
        boolean qty, location, date;
        if(txtLocation.getText().matches("[a-zA-Z]+")) {
            txtLocation.setUnFocusColor(Paint.valueOf("#276c93"));
            location = true;
        }else{
            txtLocation.setUnFocusColor(Paint.valueOf("red"));
            location = false;
        }
        if(txtDate.getText().matches("((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])")) {
            txtDate.setUnFocusColor(Paint.valueOf("#276c93"));
            date = true;
        }else{
            txtDate.setUnFocusColor(Paint.valueOf("red"));
            date = false;
        }
        if(txtQty.getText().matches("[0-9]*")) {
            txtQty.setUnFocusColor(Paint.valueOf("#276c93"));
            qty = true;
        }else{
            txtQty.setUnFocusColor(Paint.valueOf("red"));
            qty = false;
        }

        if(location==true && qty==true && date==true ){
            return true;
        }else {
            return false;
        }
    }

    public void cmbOrderIDOnAction(ActionEvent actionEvent) throws SQLException {
        obList.clear();
        orderId = (String) cmbOrderId.getValue();
        String customerId = OrderModel.loadCustomerId(orderId);
        lblCustId.setText(customerId);
        List<String> codes = OrderModel.getCodesOfOrder(orderId);
        ObservableList<String> obList = FXCollections.observableArrayList();
        for (String code : codes) {
            obList.add(code);
        }
        cmbItemCode.setItems(obList);

        insertOrdreDetailsToTable(orderId);



    }

    private void insertOrdreDetailsToTable(String orderId) {
        try {
           // ObservableList<ViewOrderTM> obList = FXCollections.observableArrayList();
            List<OrderDetails> cusList = OrderModel.getAll(orderId);

            for(OrderDetails customer : cusList) {
                obList.add(new ViewOrderTM(

                        customer.getItemCode(),
                        customer.getQtyOrdered(),
                        customer.getDriver_id(),
                        customer.getDate(),
                        customer.getLocation(),
                        customer.getQtyDelivered()

                ));
            }
            tblOrderDetail.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void cmbItemCodeOnAction(ActionEvent actionEvent) throws SQLException {
        String itemCode = (String) cmbItemCode.getValue();
        orderedItem = OrderModel.getOrderedItem(orderId, itemCode);

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

        for (int i = 0; i < tblOrderDetail.getItems().size(); i++) {
            ViewOrderTM tm = obList.get(i);

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(tm.getCode(),tm.getDriver(), tm.getDate(), tm.getLocation(), tm.getQty());
            orderDetailDTOList.add(orderDetailDTO);
        }
        try {
            boolean isOrderTableUpdated = OrderModel.updateOrderDetailAgain(orderId, orderDetailDTOList);
            if(isOrderTableUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Couldn't update!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
