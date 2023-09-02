package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import lk.ijse.spacewood.dto.Customer;
import lk.ijse.spacewood.dto.Employee;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;



public class EmployeeViewFormController implements Initializable {
    public JFXComboBox cmbEmployeeId;
    public JFXComboBox cmbEmployeeName;
    public Label lblName;
    public Label lblNic;
    public JFXTextField txtPhone;
    public JFXTextField txtWhatsapp;
    public Label lblAddress;
    public JFXTextField txtDateLeft;
    public Label lblDateJoined;

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean checkWithDate=false, checkWithoutDate = false;


        if(txtDateLeft.getText().equalsIgnoreCase("")){
            checkWithoutDate = checkValidationWithoutDate();
        }else {
            checkWithDate = checkValidationWithDate();
        }
        if (checkWithoutDate==true || checkWithDate==true) {
            String phone = txtPhone.getText();
            String whatsapp = txtWhatsapp.getText();
            String date = txtDateLeft.getText();

            boolean isUpdated = false;
            String id = (String) cmbEmployeeId.getSelectionModel().getSelectedItem();
            try {
                if (date.equalsIgnoreCase("")) {
                    isUpdated = EmployeeModel.updateEmployeeDetails(phone, whatsapp, id);
                } else {

                    isUpdated = EmployeeModel.updateEmployeeDetails(date, phone, whatsapp, id);
                }
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

    private boolean checkValidationWithoutDate() {
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

        if( phone==true && whatsapp==true ){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkValidationWithDate() {
        boolean phone, whatsapp, date;
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
        if(txtDateLeft.getText().matches("((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])")) {
            txtDateLeft.setUnFocusColor(Paint.valueOf("#276c93"));
            date = true;
        }else{
            txtDateLeft.setUnFocusColor(Paint.valueOf("red"));
            date = false;
        }
        if( phone==true && whatsapp==true && date==true){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmployeeIds();
        loadEmployeeNames();
    }

    private void loadEmployeeNames() {
        try {
            List<String> names = EmployeeModel.getNames();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : names) {
                obList.add(id);
            }
            cmbEmployeeName.setItems(obList);
            // cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadEmployeeIds() {
        try {
            List<String> ids = EmployeeModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbEmployeeId.setItems(obList);
            // cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    public void cmbEmployeeNameOnAction(ActionEvent actionEvent) {
        String name = (String) cmbEmployeeName.getSelectionModel().getSelectedItem();

        try {
            Employee employee = EmployeeModel.searchByName(name);
            lblName.setText(employee.getFirstName()+" "+ employee.getLastName());
            lblAddress.setText(employee.getAddress());
            txtPhone.setText(employee.getPhone());
            txtWhatsapp.setText(employee.getWhatsapp());
            cmbEmployeeId.setValue(employee.getId());
            lblNic.setText(employee.getNic());
            lblDateJoined.setText(employee.getDateJoined());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbEmployeeIdONAction(ActionEvent actionEvent) {
        String id = (String) cmbEmployeeId.getSelectionModel().getSelectedItem();

        try {
            Employee employee = EmployeeModel.searchById(id);
            lblName.setText(employee.getFirstName()+" "+ employee.getLastName());
            lblAddress.setText(employee.getAddress());
            txtPhone.setText(employee.getPhone());
            txtWhatsapp.setText(employee.getWhatsapp());
            cmbEmployeeName.setValue(employee.getFirstName());
            lblNic.setText(employee.getNic());
            lblDateJoined.setText(employee.getDateJoined());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
