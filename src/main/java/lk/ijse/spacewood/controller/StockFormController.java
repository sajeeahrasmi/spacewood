package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockFormController implements Initializable {

    public AnchorPane componentPane;
    public Glow glow = new Glow();
    public  Glow unglow = null;
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color;";
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
   // private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    public JFXButton btnViewLog;
    public JFXButton btnAddWoodcut;
    public JFXButton btnViewWoodcut;

    {
        glow.setLevel(34.5);
    }

    public void btnViewLogsOnAction(ActionEvent actionEvent) throws IOException {
        btnAddWoodcut.setEffect(unglow);
        btnViewWoodcut.setEffect(unglow);
        btnViewLog.setEffect(glow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/stock_view_log_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            componentPane.getChildren().clear();
            componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void btnAddWoodcutsOnAction(ActionEvent actionEvent) throws IOException {
        btnAddWoodcut.setEffect(glow);
        btnViewWoodcut.setEffect(unglow);
        btnViewLog.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/stock_add_woodcut_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            componentPane.getChildren().clear();
            componentPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnViewWoodcutsOnAction(ActionEvent actionEvent) throws IOException {
        btnAddWoodcut.setEffect(unglow);
        btnViewWoodcut.setEffect(glow);
        btnViewLog.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/stock_view_woodcut_form.fxml"));
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
        try {
            btnViewLogsOnAction(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnViewLogEntered(MouseEvent mouseEvent) {
        btnViewLog.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnViewLogExited(MouseEvent mouseEvent) {
        btnViewLog.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnAddWoodcutEntered(MouseEvent mouseEvent) {
        btnAddWoodcut.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnAddWoodcutExited(MouseEvent mouseEvent) {
        btnAddWoodcut.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnViewWoodcutEntered(MouseEvent mouseEvent) {
        btnViewWoodcut.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnViewWoodcutExited(MouseEvent mouseEvent) {
        btnViewWoodcut.setStyle(IDLE_BUTTON_STYLE);
    }
}
