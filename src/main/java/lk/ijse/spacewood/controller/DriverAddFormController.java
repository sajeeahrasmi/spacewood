package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.DriverModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DriverAddFormController implements Initializable {
    public Label lblDriverId;
    public JFXTextField txtFirstName;
    public JFXTextField txtNo;
    public JFXTextField txtStreet2;
    public JFXTextField txtPhone;
    public JFXTextField txtVehicle;
    public JFXTextField txtLastName;
    public JFXTextField txtStreet1;
    public JFXTextField txtCity;
    public JFXTextField txtWhatsapp;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check) {
            String id = lblDriverId.getText(), name = txtFirstName.getText() + " " + txtLastName.getText();
            String address = txtNo.getText() + ", " + txtStreet1.getText() + ", " + txtStreet2.getText() + ", " + txtCity.getText();
            String phone = txtPhone.getText(), whatsapp = txtWhatsapp.getText();
            String vehicle = txtVehicle.getText();
            try {
                boolean isDriverAdded = DriverModel.addDriver(id, name, address, phone, whatsapp, vehicle);
                if (isDriverAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Driver added successfully!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Couldn't add driver!").show();
                }
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }
    private boolean checkValidation() {
        boolean fName, lName, phone, whatsapp;
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
        if(fName==true && lName==true && phone==true && whatsapp==true){
            return true;
        }else {
            return false;
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextDriverID();
    }

    private void generateNextDriverID() {
        try {
            String nextID = DriverModel.nextDriverID();
            lblDriverId.setText(nextID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
