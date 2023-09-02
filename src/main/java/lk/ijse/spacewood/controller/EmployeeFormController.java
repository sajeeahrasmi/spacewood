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

public class EmployeeFormController implements Initializable {
    public AnchorPane EmpComPane;
    public JFXButton btnAddEmp;
    public JFXButton btnViewEmp;
    public Glow glow = new Glow();
    public  Glow unglow = null;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
   // private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
   private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color;";

    {
        glow.setLevel(34.5);
    }

    public void btnAddEmpOnAction(ActionEvent actionEvent) throws IOException {
        btnAddEmp.setEffect(glow);
        btnViewEmp.setEffect(unglow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee_add_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            EmpComPane.getChildren().clear();
            EmpComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnViewEmpOnAction(ActionEvent actionEvent) throws IOException {
        btnAddEmp.setEffect(unglow);
        btnViewEmp.setEffect(glow);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee_view_form.fxml"));
        Pane pane = (Pane) fxmlLoader.load();
        try{
            EmpComPane.getChildren().clear();
            EmpComPane.getChildren().add(pane);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            btnAddEmpOnAction(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddEmpEntered(MouseEvent mouseEvent) {
        btnAddEmp.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnAddEmpExited(MouseEvent mouseEvent) {
        btnAddEmp.setStyle(IDLE_BUTTON_STYLE);
    }

    public void btnViewEmpEntered(MouseEvent mouseEvent) {
        btnViewEmp.setStyle(HOVERED_BUTTON_STYLE);
    }

    public void btnViewEmpExited(MouseEvent mouseEvent) {
        btnViewEmp.setStyle(IDLE_BUTTON_STYLE);
    }
}
