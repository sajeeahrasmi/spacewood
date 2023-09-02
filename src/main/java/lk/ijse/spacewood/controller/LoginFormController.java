package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.spacewood.model.LoginModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    public JFXTextField txtFieldUser;

    public AnchorPane root;
    public JFXPasswordField txtFieldPass;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        /*AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Manage");
        stage.centerOnScreen();*/
        String user = txtFieldUser.getText();
        String pass = txtFieldPass.getText();

        try {
            boolean isValid = LoginModel.validLoginDetail(user,pass);
            if(isValid){
               // System.out.println("correct");
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("SHAZNA CARPENTERS'");
                stage.centerOnScreen();
            }else {
               new Alert(Alert.AlertType.ERROR, "Username or Password incorrect").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtUserOnAction(ActionEvent actionEvent) {
        txtFieldPass.requestFocus();
    }

    public void txtPassOnAction(ActionEvent actionEvent) {

    }
}
