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
import lk.ijse.spacewood.dto.Log;
import lk.ijse.spacewood.dto.Order;
import lk.ijse.spacewood.dto.Supplier;
import lk.ijse.spacewood.dto.tm.CustomerTM;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;



public class SupplierViewFormController implements Initializable {

    public JFXComboBox cmbSupplierId;
    public JFXComboBox cmbSupplierName;
    public Label lblName;
    public Label lblAddress;
    public JFXTextField txtPhone;
    public JFXTextField txtWhatsapp;
    public TableView tblSupplierDetail;
    public TableColumn colId;
    public TableColumn colType;
    public TableColumn colPermit;
    public TableColumn colLocation;
    public TableColumn colDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplierID();
        loadSupplierNames();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPermit.setCellValueFactory(new PropertyValueFactory<>("permitNo"));
    }

    private void loadSupplierNames() {
        try {
            List<String> names = SupplierModel.getNames();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String name : names) {
                obList.add(name);
            }
            cmbSupplierName.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadSupplierID() {
        try {
            List<String> ids = SupplierModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbSupplierId.setItems(obList);
            // cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    public void cmbSupplierIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbSupplierId.getSelectionModel().getSelectedItem();

        try {
            Supplier supplier = SupplierModel.searchById(id);
            lblName.setText(supplier.getFirst_name()+" "+ supplier.getLast_name());
            lblAddress.setText(supplier.getAddress());
            txtPhone.setText(supplier.getPhone());
            txtWhatsapp.setText(supplier.getWhatsapp());
            cmbSupplierName.setValue(supplier.getFirst_name());
            loadSupplierTable(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupplierTable(String id) {

        try {
            ObservableList<Log> obList = FXCollections.observableArrayList();
            List<Log> cusList = SupplierModel.getAll(id);

            for(Log log : cusList) {
                obList.add(new Log(
                        log.getLogId(),
                        log.getType(),
                        log.getPermitNo(),
                        log.getLocation(),
                        log.getDate()
                ));
            }
            tblSupplierDetail.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void cmbSupplierNameOnAction(ActionEvent actionEvent) {
        String name = (String) cmbSupplierName.getSelectionModel().getSelectedItem();

        try {
            Supplier supplier = SupplierModel.searchByName(name);
            lblName.setText(supplier.getFirst_name()+" "+ supplier.getLast_name());
            lblAddress.setText(supplier.getAddress());
            txtPhone.setText(supplier.getPhone());
            txtWhatsapp.setText(supplier.getWhatsapp());
            cmbSupplierId.setValue(supplier.getId());
            // loadCustomerTable(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateONAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check) {
            String phone = txtPhone.getText();
            String whatsapp = txtWhatsapp.getText();
            String id = (String) cmbSupplierId.getSelectionModel().getSelectedItem();

            try {
                boolean isUpdated = SupplierModel.updateSupplierDetails(phone, whatsapp, id);
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
}
