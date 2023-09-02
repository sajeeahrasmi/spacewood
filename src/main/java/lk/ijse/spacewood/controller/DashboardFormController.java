package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    public JFXButton txtBtnCust;
    public JFXButton txtBtnDriver;
    public JFXButton txtBtnSup;
    public JFXButton txtBtnStock;
    public JFXButton txtBtnEmp;
    public AnchorPane root;
    public  AnchorPane componentPane;
   public Glow glow = new Glow();
   public  Glow unglow = null;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
   // private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color;";

    {
        glow.setLevel(34.5);
    }
    public  void btnCustOnAction(ActionEvent actionEvent) throws IOException {

        txtBtnCust.setEffect(glow);
        txtBtnDriver.setEffect(unglow);
        txtBtnSup.setEffect(unglow);
        txtBtnStock.setEffect(unglow);
        txtBtnEmp.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
           componentPane.getChildren().clear();
           componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public  void btnSupOnAction(ActionEvent actionEvent) throws IOException {
        //txtBtnSup.armedProperty();
        txtBtnCust.setEffect(unglow);
        txtBtnDriver.setEffect(unglow);
        txtBtnSup.setEffect(glow);
        txtBtnStock.setEffect(unglow);
        txtBtnEmp.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplier_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            componentPane.getChildren().clear();
            componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnStockOnAction(ActionEvent actionEvent) throws IOException {

        txtBtnCust.setEffect(unglow);
        txtBtnDriver.setEffect(unglow);
        txtBtnSup.setEffect(unglow);
        txtBtnStock.setEffect(glow);
        txtBtnEmp.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/stock_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            componentPane.getChildren().clear();
            componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnEmpOnAction(ActionEvent actionEvent) throws IOException {
        txtBtnCust.setEffect(unglow);
        txtBtnDriver.setEffect(unglow);
        txtBtnSup.setEffect(unglow);
        txtBtnStock.setEffect(unglow);
        txtBtnEmp.setEffect(glow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            componentPane.getChildren().clear();
            componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnDriOnAction(ActionEvent actionEvent) throws IOException {

        txtBtnCust.setEffect(unglow);
        txtBtnDriver.setEffect(glow);
        txtBtnSup.setEffect(unglow);
        txtBtnStock.setEffect(unglow);
        txtBtnEmp.setEffect(unglow);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/driver_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            componentPane.getChildren().clear();
            componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_form.fxml"));
        try{
        Pane pane = (Pane) fxmlLoader.load();

            componentPane.getChildren().clear();
            componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void custMouseEntered(MouseEvent mouseEvent) {
        txtBtnCust.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void custMouseExited(MouseEvent mouseEvent) {
        txtBtnCust.setStyle(IDLE_BUTTON_STYLE);
    }

    public void supMouseEntered(MouseEvent mouseEvent) {
        txtBtnSup.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void supMouseExited(MouseEvent mouseEvent) {
        txtBtnSup.setStyle(IDLE_BUTTON_STYLE);
    }

    public void stockMouseEntered(MouseEvent mouseEvent) {
        txtBtnStock.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void stockMouseExited(MouseEvent mouseEvent) {
        txtBtnStock.setStyle(IDLE_BUTTON_STYLE);
    }

    public void EmpMouseEntered(MouseEvent mouseEvent) {
        txtBtnEmp.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void EmpMouseExited(MouseEvent mouseEvent) {
        txtBtnEmp.setStyle(IDLE_BUTTON_STYLE);
    }

    public void DriverMouseEntered(MouseEvent mouseEvent) {
        txtBtnDriver.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void DriverMouseExited(MouseEvent mouseEvent) {
        txtBtnDriver.setStyle(IDLE_BUTTON_STYLE);
    }
}
