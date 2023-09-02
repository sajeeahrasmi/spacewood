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
import lk.ijse.spacewood.dto.Order;
import lk.ijse.spacewood.dto.tm.CustomerTM;
import lk.ijse.spacewood.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;



public class CustomerViewFormController implements Initializable {


    public JFXComboBox cmbCustId;
    public JFXComboBox cmbCustName;
    public Label lblName;
    public Label lblAddress;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colQty;
    public TableColumn<?,?> colCost;
    public TableColumn<?,?> colDate;
    public JFXTextField txtPhone;
    public JFXTextField txtWhatsapp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomerIDs();
        loadCustomerNames();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadCustomerNames() {
        try {
            List<String> names = CustomerModel.getNames();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String name : names) {
                obList.add(name);
            }
            cmbCustName.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadCustomerIDs() {
        try {
            List<String> ids = CustomerModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbCustId.setItems(obList);
           // cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    public void cmbCustIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbCustId.getSelectionModel().getSelectedItem();

        try {
            Customer customer = CustomerModel.searchById(id);
            lblName.setText(customer.getFirst_name()+" "+ customer.getLast_name());
            lblAddress.setText(customer.getAddress());
            txtPhone.setText(customer.getPhone());
            txtWhatsapp.setText(customer.getWhatsapp());
            cmbCustName.setValue(customer.getFirst_name());
            loadCustomerTable(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadCustomerTable(String id) {
        String customerId = id;
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Order> cusList = CustomerModel.getAll(id);

            for(Order customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getOrder_id(),
                        customer.getTotal(),
                        customer.getDate()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }

    }

    public void cmbCustNameOnAction(ActionEvent actionEvent) {
        String name = (String) cmbCustName.getSelectionModel().getSelectedItem();

        try {
            Customer customer = CustomerModel.searchByName(name);
            lblName.setText(customer.getFirst_name()+" "+ customer.getLast_name());
            lblAddress.setText(customer.getAddress());
            txtPhone.setText(customer.getPhone());
            txtWhatsapp.setText(customer.getWhatsapp());

            cmbCustId.setValue(customer.getId());
            loadCustomerTable(customer.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check == true){
            String phone = txtPhone.getText();
        String whatsapp = txtWhatsapp.getText();
        String id = (String) cmbCustId.getSelectionModel().getSelectedItem();

        try {
            boolean isUpdated = CustomerModel.updateCustomerDetails(phone, whatsapp, id);
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

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = (String) cmbCustId.getSelectionModel().getSelectedItem();
        try {
            boolean haveOrders = CustomerModel.checkCustomerOrders(id);
            if(haveOrders){
                new Alert(Alert.AlertType.ERROR, "Couldn't delete! Customer has orders").show();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            boolean isDeleted = CustomerModel.deleteCustomer(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully!").show();

            }else {
                new Alert(Alert.AlertType.ERROR, "Couldn't delete!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL error").show();
            throw new RuntimeException(e);
        }
    }
}
