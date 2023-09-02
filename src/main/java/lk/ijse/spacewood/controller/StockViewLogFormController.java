package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.spacewood.dto.Log;
import lk.ijse.spacewood.dto.Supplier;
import lk.ijse.spacewood.model.StockModel;
import lk.ijse.spacewood.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StockViewLogFormController implements Initializable {

    public JFXComboBox cmbType;
    public TableView tblLog;
    public TableColumn colLogId;
    public TableColumn colType;
    public TableColumn colPermitNo;
    public TableColumn colLocation;
    public TableColumn colSupId;
    public TableColumn colUsed;
    public Label lblMahogany;
    public Label lblJak;
    public Label lblTeak;
    public Label lblSooriyamara;
    public Label lblKumbuk;

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String type = (String) cmbType.getValue();
        ObservableList<Log> obList = FXCollections.observableArrayList();
        try {
            List<Log> logList = StockModel.getSelected(type);
            for(Log log : logList) {
                obList.add(new Log(
                        log.getLogId(),
                        log.getType(),
                        log.getSupId(),
                        log.getLocation(),
                        log.getPermitNo(),
                        log.getUsed()
                ));
            }
            tblLog.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
        loadLogTypes();
        loadTable();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colLogId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colPermitNo.setCellValueFactory(new PropertyValueFactory<>("permitNo"));
        colUsed.setCellValueFactory(new PropertyValueFactory<>("used"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void loadTable() {
        ObservableList<Log> obList = FXCollections.observableArrayList();
        try {
            List<Log> logList = StockModel.getAll();
            for(Log log : logList) {
                obList.add(new Log(
                        log.getLogId(),
                        log.getType(),
                        log.getSupId(),
                        log.getLocation(),
                        log.getPermitNo(),
                        log.getUsed()
                ));
            }
            tblLog.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLogTypes() {
        List<String> types = new ArrayList<>();
        types.add("Mahogany");
        types.add("Jak");
        types.add("Teak");
        types.add("Sooriyamara");
        types.add("Kumbuk");
        ObservableList<String> obList = FXCollections.observableArrayList();
        for (String type : types) {
            obList.add(type);
        }
        cmbType.setItems(obList);
    }

    private void setLabels() {
        String[] names = {"Mahogany", "Jak", "Teak", "Sooriyamara", "Kumbuk"};
        int[] count = new int[5];
        for (int i = 0; i < names.length; i++) {
            try {
                count[i] =StockModel.getCount(names[i]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        lblMahogany.setText(String.valueOf(count[0]));
        lblJak.setText(String.valueOf(count[1]));
        lblTeak.setText(String.valueOf(count[2]));
        lblSooriyamara.setText(String.valueOf(count[3]));
        lblKumbuk.setText(String.valueOf(count[4]));
    }
}
