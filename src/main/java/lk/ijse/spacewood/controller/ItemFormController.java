package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.ItemModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    public Label lblCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public VBox vbox;
   // public  JPanel panel = new JPanel();



    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = lblCode.getText();
        String description = txtDescription.getText();
        double price = Double.parseDouble(txtPrice.getText());
        try {
            boolean isItemAdded = ItemModel.addItem(id, description,price);
            if(isItemAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Item added successfully!").show();

            }
            else {
                new Alert(Alert.AlertType.ERROR, "Couldn't add Item!").show();
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
         }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextItemID();
    }

    private void generateNextItemID() {
        try {
            String nextID = ItemModel.nextItemID();
            lblCode.setText(nextID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
