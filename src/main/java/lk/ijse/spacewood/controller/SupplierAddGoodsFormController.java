package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.ijse.spacewood.dto.Log;
import lk.ijse.spacewood.dto.OrderDTO;
import lk.ijse.spacewood.dto.Supplier;
import lk.ijse.spacewood.dto.tm.AddLogTM;
import lk.ijse.spacewood.dto.tm.AddOrderTM;
import lk.ijse.spacewood.model.OrderModel;
import lk.ijse.spacewood.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class SupplierAddGoodsFormController implements Initializable {

    public Label lblDate;
    public JFXComboBox cmbSupplierName;
    public JFXComboBox cmbSupplierId;
    public JFXTextField txtLogId;
    public JFXTextField txtDescription;
    public JFXComboBox cmbType;
    public JFXTextField txtPermit;
    public JFXTextField txtLocation;
    public TableView<AddLogTM> tblAddLogs;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colDescription;
    public TableColumn<?, ?> colType;
    public TableColumn<?, ?> colPermit;
    public TableColumn<?, ?> colLocation;
    public JFXButton btnAdd;
    private ObservableList<AddLogTM> obList = FXCollections.observableArrayList();

    public void cmbSupplierNameOnAction(ActionEvent actionEvent) {
        String name = (String) cmbSupplierName.getSelectionModel().getSelectedItem();

        try {
            Supplier supplier = SupplierModel.searchByName(name);
            cmbSupplierId.setValue(supplier.getId());
            // loadCustomerTable(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbSupplierIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbSupplierId.getSelectionModel().getSelectedItem();

        try {
            Supplier supplier = SupplierModel.searchById(id);
            cmbSupplierName.setValue(supplier.getFirst_name());
            // loadCustomerTable(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String supId = (String) cmbSupplierId.getValue();
        String date  = lblDate.getText();

        List<Log> logList = new ArrayList<>();
        for (int i = 0; i < tblAddLogs.getItems().size(); i++) {
            AddLogTM tm = obList.get(i);

            Log log = new Log(tm.getId(), tm.getType(), supId, tm.getLocation(),  tm.getPermitNo(), tm.getDescription(), date);
            logList.add(log);

            try {
                boolean isLogTableUpdated = SupplierModel.updateLogTable(logList);
                if(isLogTableUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "added successfully!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Couldn't add!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check) {
            String supplierId = (String) cmbSupplierId.getValue();
            String logId = txtLogId.getText();
            String description = txtDescription.getText();
            String type = (String) cmbType.getValue();
            String permitNo = txtPermit.getText();
            String location = txtLocation.getText();

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblAddLogs.getItems().size(); i++) {
                    if (colId.getCellData(i).equals(logId)) {

                        obList.get(i).setDescription(description);
                        obList.get(i).setType(type);
                        obList.get(i).setPermitNo(permitNo);
                        obList.get(i).setLocation(location);

                        tblAddLogs.refresh();

                        return;
                    }

                }
            }

            AddLogTM tm = new AddLogTM(logId, description, type, permitNo, location);

            obList.add(tm);
            tblAddLogs.setItems(obList);


        }
    }

    private boolean checkValidation() {
        boolean location;
        if(txtLocation.getText().matches("[a-zA-Z]+")) {
            txtLocation.setUnFocusColor(Paint.valueOf("#276c93"));
            location = true;
        }else{
            txtLocation.setUnFocusColor(Paint.valueOf("red"));
            location = false;
        }

        if( location==true ){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        loadSupplierID();
        loadSupplierNames();
        loadLogTypes();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPermit.setCellValueFactory(new PropertyValueFactory<>("permitNo"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void loadLogTypes() {
        List<String> types = new ArrayList<>();
        types.add("Mahogany");
        types.add("Jak");
        types.add("Teak");
        types.add("Suriyamara");
        types.add("Kumbuk");
        ObservableList<String> obList = FXCollections.observableArrayList();
        for (String type : types) {
            obList.add(type);
        }
        cmbType.setItems(obList);

    }

    private void loadSupplierNames() {
        try {
            List<String> names = SupplierModel.getNames();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String name : names) {
                obList.add(name);
            }
            cmbSupplierName.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadSupplierID() {
        try {
            List<String> ids = SupplierModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbSupplierId.setItems(obList);
            // cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
}
