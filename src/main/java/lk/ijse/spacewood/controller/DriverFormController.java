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

public class DriverFormController implements Initializable {
    public AnchorPane driverComPane;
    public JFXButton btnAddDriver;
    public JFXButton btnViewDriver;
    public Glow glow = new Glow();
    public  Glow unglow = null;
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
   // private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color;";

    {
        glow.setLevel(34.5);
    }

    public void btnAddDriverOnAction(ActionEvent actionEvent) throws IOException {
        btnViewDriver.setEffect(unglow);
        btnAddDriver.setEffect(glow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/driver_add_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            driverComPane.getChildren().clear();
            driverComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnViewDriverOnAction(ActionEvent actionEvent) throws IOException {
        btnAddDriver.setEffect(unglow);
        btnViewDriver.setEffect(glow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/driver_view_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            driverComPane.getChildren().clear();
            driverComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            btnAddDriverOnAction(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddDriverEntered(MouseEvent mouseEvent) {
        btnAddDriver.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnAddDriverExited(MouseEvent mouseEvent) {
        btnAddDriver.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnViewDriverEntered(MouseEvent mouseEvent) {
        btnViewDriver.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnViewDriverExited(MouseEvent mouseEvent) {
        btnViewDriver.setStyle(IDLE_BUTTON_STYLE);
    }
}
