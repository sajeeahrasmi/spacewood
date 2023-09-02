package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class EmployeeAddFormController implements Initializable {
    public JFXTextField txtNic;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtNo;
    public JFXTextField txtPhone;
    public JFXTextField txtDate;
    public Label lblEmployeeId;
    public JFXTextField txtStreet1;
    public JFXTextField txtStreet2;
    public JFXTextField txtCity;
    public JFXTextField txtWhatsapp;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check) {
            String id = lblEmployeeId.getText(), fName = txtFirstName.getText(), lName = txtLastName.getText();
            String address = txtNo.getText() + ", " + txtStreet1.getText() + ", " + txtStreet2.getText() + ", " + txtCity.getText();
            String phone = txtPhone.getText(), whatsapp = txtWhatsapp.getText();
            String nic = txtNic.getText();
            String dateJoined = txtDate.getText();
            try {
                boolean isEmployeeAdded = EmployeeModel.addEmployee(id, fName, lName, address, phone, whatsapp, nic, dateJoined);
                if (isEmployeeAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee added successfully!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Couldn't add Employee!").show();
                }
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkValidation() {
        boolean fName, lName, phone, whatsapp, date;
        if(txtFirstName.getText().matches("[a-zA-Z]+")) {
            txtFirstName.setUnFocusColor(Paint.valueOf("#276c93"));
            fName = true;
        }else{
            txtFirstName.setUnFocusColor(Paint.valueOf("red"));
            fName = false;
        }
        if(txtLastName.getText().matches("[a-zA-Z]+")) {
            txtLastName.setUnFocusColor(Paint.valueOf("#276c93"));
            lName = true;
        }else{
            txtLastName.setUnFocusColor(Paint.valueOf("red"));
            lName = false;
        }
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
        if(txtDate.getText().matches("((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])")) {
            txtDate.setUnFocusColor(Paint.valueOf("#276c93"));
            date = true;
        }else{
            txtDate.setUnFocusColor(Paint.valueOf("red"));
            date = false;
        }
        if(fName==true && lName==true && phone==true && whatsapp==true && date==true){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextEmployeeID();
    }

    private void generateNextEmployeeID() {
        try {
            String nextID = EmployeeModel.nextEmployeeID();
            lblEmployeeId.setText(nextID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
