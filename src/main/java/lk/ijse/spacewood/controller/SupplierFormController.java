package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    public AnchorPane supComPane;
    public JFXButton btnAddSup;
    public JFXButton btnViewSup;
    public JFXButton btnAddLog;
    public Glow glow = new Glow();
    public  Glow unglow = null;
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color;";
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
   // private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    {
        glow.setLevel(34.5);
    }

    public void btnAddSupOnAction(ActionEvent actionEvent) throws IOException {

        btnAddLog.setEffect(unglow);
        btnViewSup.setEffect(unglow);
        btnAddSup.setEffect(glow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplier_add_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            supComPane.getChildren().clear();
            supComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

    public void btnViewSupOnAction(ActionEvent actionEvent) throws IOException {
        btnAddLog.setEffect(unglow);
        btnViewSup.setEffect(glow);
        btnAddSup.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplier_view_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            supComPane.getChildren().clear();
            supComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnAddGoodsSupOnAction(ActionEvent actionEvent) throws IOException {
        btnAddLog.setEffect(glow);
        btnViewSup.setEffect(unglow);
        btnAddSup.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplier_add_goods_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            supComPane.getChildren().clear();
            supComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAddSupOnAction(new ActionEvent());
    }

    public void btnAddSupEntered(MouseEvent mouseEvent) {
        btnAddSup.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnAddSupExited(MouseEvent mouseEvent) {
        btnAddSup.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnAddLogEntered(MouseEvent mouseEvent) {
        btnAddLog.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnViewSupEntered(MouseEvent mouseEvent) {
        btnViewSup.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnAddLogExited(MouseEvent mouseEvent) {
        btnAddLog.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnViewSupExited(MouseEvent mouseEvent) {
        btnViewSup.setStyle(IDLE_BUTTON_STYLE);
    }
}
