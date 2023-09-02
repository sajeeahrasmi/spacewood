package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.spacewood.model.CustomerModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CustomerAddFormController implements Initializable {


    public Label lblCustID;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtNo;
    public JFXTextField txtStreet2;
    public JFXTextField txtPhone;
    public JFXTextField txtStreet1;
    public JFXTextField txtCity;
    public JFXTextField txtWhatsapp;
    public JFXButton btnAddOrder;
    public AnchorPane compPane;


    public void btnSaveCustOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check == true){
            String id = lblCustID.getText(), fName = txtFirstName.getText(), lName = txtLastName.getText();
        String address = txtNo.getText() + ", " + txtStreet1.getText() + ", " + txtStreet2.getText() + ", " + txtCity.getText();
        String phone = txtPhone.getText(), whatsapp = txtWhatsapp.getText();
        try {
            boolean isCustomerAdded = CustomerModel.addCustomer(id, fName, lName, address, phone, whatsapp);
            if (isCustomerAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer added successfully!").show();
                //btnAddOrder.setDisable(false);
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer added unsuccessfully!").show();
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
       generateNextCustomerID();
       // checkValidation();

    }



    private void generateNextCustomerID() {
        try {
            String nextID = CustomerModel.nextCustomerID();
            lblCustID.setText(nextID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOrderOnAction(ActionEvent actionEvent) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_add_order_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            compPane.getChildren().clear();
            compPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }*/


       // CustomerAddOrderFormController.addOrderToCustomer(lblCustID.getText());
    }
}
