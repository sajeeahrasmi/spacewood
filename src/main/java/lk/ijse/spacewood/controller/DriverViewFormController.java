package lk.ijse.spacewood.controller;

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
import lk.ijse.spacewood.dto.Customer;
import lk.ijse.spacewood.dto.Driver;
import lk.ijse.spacewood.dto.Order;
import lk.ijse.spacewood.dto.OrderDetails;
import lk.ijse.spacewood.dto.tm.CustomerTM;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.DriverModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DriverViewFormController implements Initializable {
    public JFXComboBox cmbDriverID;
    public JFXComboBox cmbDriverName;
    public JFXTextField txtPhone;
    public JFXTextField txtVehicle;
    public JFXTextField txtWhatsapp;
    public Label lblName;
    public Label lblAddress;
    public TableView tblDriver;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colLocation;
    public TableColumn colDate;

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check) {
            String phone = txtPhone.getText();
            String whatsapp = txtWhatsapp.getText();
            String vehicle = txtVehicle.getText();
            String id = (String) cmbDriverID.getSelectionModel().getSelectedItem();

            try {
                boolean isUpdated = DriverModel.updateDriverDetails(phone, whatsapp, id, vehicle);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Couldn't update!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL error").show();
                throw new RuntimeException(e);
            }
        }
    }
    private boolean checkValidation() {
        boolean phone, whatsapp;
        if(txtPhone.getText().matches("[0-9]*")) {
            txtPhone.setUnFocusColor(Paint.valueOf("#276c93"));
            phone = true;
        }else{
            txtPhone.setUnFocusColor(Paint.valueOf("red"));
            phone = false;
        }
        if(txtWhatsapp.getText().matches("[0-9]*")) {
            txtWhatsapp.setUnFocusColor(Paint.valueOf("#276c93"));
            whatsapp = true;
        }else{
            txtWhatsapp.setUnFocusColor(Paint.valueOf("red"));
            whatsapp = false;
        }
        if( phone==true && whatsapp==true){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDriverIDs();
        loadDriverNames();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyDelivered"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadDriverNames() {
        try {
            List<String> names = DriverModel.getNames();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String name : names) {
                obList.add(name);
            }
            cmbDriverName.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadDriverIDs() {
        try {
            List<String> ids = DriverModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbDriverID.setItems(obList);
            // cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    public void cmbDriverNameOnAction(ActionEvent actionEvent) {
        String name = (String) cmbDriverName.getSelectionModel().getSelectedItem();

        try {
            Driver driver = DriverModel.searchByName(name);
            lblName.setText(driver.getName());
            lblAddress.setText(driver.getAddress());
            txtPhone.setText(driver.getPhone());
            txtWhatsapp.setText(driver.getWhatsapp());
            txtVehicle.setText(driver.getVehicle());
            cmbDriverID.setValue(driver.getId());
            loadDriverTable(driver.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDriverTable(String id) {

        try {
            ObservableList<OrderDetails> obList = FXCollections.observableArrayList();
            List<OrderDetails> driverList = DriverModel.getAll(id);

            for(OrderDetails driver : driverList) {
                obList.add(new OrderDetails(
                        driver.getOrder_id(),
                        driver.getItemCode(),
                        driver.getQtyDelivered(),
                        driver.getDriver_id(),
                        driver.getDate(),
                        driver.getLocation(),
                        driver.getQtyOrdered()
                ));
            }
            tblDriver.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void cmbDriverIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbDriverID.getSelectionModel().getSelectedItem();

        try {
            Driver driver = DriverModel.searchById(id);
            lblName.setText(driver.getName());
            lblAddress.setText(driver.getAddress());
            txtPhone.setText(driver.getPhone());
            txtWhatsapp.setText(driver.getWhatsapp());
            txtVehicle.setText(driver.getVehicle());
            cmbDriverName.setValue(driver.getName());
            loadDriverTable(driver.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
