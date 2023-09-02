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

public class CustomerFormController implements Initializable {


    public AnchorPane custComPane;
    public AnchorPane mainPane;
    public JFXButton btnViewCust;
    public JFXButton btnAddCust;
    public JFXButton btnAddOrder;
    public JFXButton btnViewOrder;
    public Glow glow = new Glow();
    public  Glow unglow = null;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
   // private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
   private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color;";
    {  glow.setLevel(34.5);
    }

    public void btnAddCustOnAction(ActionEvent actionEvent) throws IOException {
        btnAddCust.setEffect(glow);
        btnViewCust.setEffect(unglow);
        btnAddOrder.setEffect(unglow);
        btnViewOrder.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_add_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();

            custComPane.getChildren().clear();
             custComPane.getChildren().add(pane);



    }

    public void btnViewCustOnAction(ActionEvent actionEvent) throws IOException {
        btnAddCust.setEffect(unglow);
        btnViewCust.setEffect(glow);
        btnAddOrder.setEffect(unglow);
        btnViewOrder.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_view_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();

            custComPane.getChildren().clear();
            custComPane.getChildren().add(pane);

    }

    public void btnAddOrderOnAction(ActionEvent actionEvent) throws IOException {
        btnAddCust.setEffect(unglow);
        btnViewCust.setEffect(unglow);
        btnAddOrder.setEffect(glow);
        btnViewOrder.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_add_order_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            custComPane.getChildren().clear();
            custComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnViewOrderOnAction(ActionEvent actionEvent) throws IOException {
        btnAddCust.setEffect(unglow);
        btnViewCust.setEffect(unglow);
        btnAddOrder.setEffect(unglow);
        btnViewOrder.setEffect(glow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_view_order_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            custComPane.getChildren().clear();
            custComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
            btnAddCustOnAction(new ActionEvent());
    }

    public void btnAddCustEntered(MouseEvent mouseEvent) {
        btnAddCust.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnViewCustEntered(MouseEvent mouseEvent) {
        btnViewCust.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnAddOrderEntered(MouseEvent mouseEvent) {
        btnAddOrder.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnViewOrderEntered(MouseEvent mouseEvent) {
        btnViewOrder.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnAddCustExited(MouseEvent mouseEvent) {
        btnAddCust.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnViewCustExited(MouseEvent mouseEvent) {
        btnViewCust.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnAddOrderExited(MouseEvent mouseEvent) {
        btnAddOrder.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnViewOrderExited(MouseEvent mouseEvent) {
        btnViewOrder.setStyle(IDLE_BUTTON_STYLE);
    }
}
