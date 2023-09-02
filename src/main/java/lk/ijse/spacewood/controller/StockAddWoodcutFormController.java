package lk.ijse.spacewood.controller;

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
import lk.ijse.spacewood.dto.OrderDTO;
import lk.ijse.spacewood.dto.Stock;
import lk.ijse.spacewood.dto.tm.AddOrderTM;
import lk.ijse.spacewood.model.OrderModel;
import lk.ijse.spacewood.model.StockModel;
import lk.ijse.spacewood.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class StockAddWoodcutFormController implements Initializable {

    public JFXTextField txtLength;
    public JFXTextField txtHeight;
    public JFXTextField txtWidth;
    public TableView tblWoodcut;
    public TableColumn colWoodcutId;
    public TableColumn colType;
    public TableColumn colLength;
    public TableColumn colWidth;
    public TableColumn colHeigth;
    public Label lblType;
    public JFXTextField txtQty;
    public TableColumn colQty;
    public TableColumn colLogId;
    public JFXComboBox cmbLogId;
    public JFXTextField txtWoodId;
    public String logId;
    private ObservableList<Stock> obList = FXCollections.observableArrayList();

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if(check) {
            String type = lblType.getText();
            String woodcutId = txtWoodId.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double length = Double.parseDouble(txtLength.getText());
            double width = Double.parseDouble(txtWidth.getText());
            double heigth = Double.parseDouble(txtHeight.getText());

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblWoodcut.getItems().size(); i++) {
                    if (colWoodcutId.getCellData(i).equals(woodcutId)) {
                        obList.get(i).setQty(qty);
                        obList.get(i).setHeight(heigth);
                        obList.get(i).setLength(length);
                        obList.get(i).setWidth(width);

                        tblWoodcut.refresh();

                        return;
                    }
                }
            }

            Stock tm = new Stock(woodcutId, type, length, width, heigth, qty, logId);

            obList.add(tm);
            tblWoodcut.setItems(obList);
        }

    }

    private boolean checkValidation() {
        boolean qty, length, width, height;
        if(txtQty.getText().matches("[0-9]*")) {
            txtQty.setUnFocusColor(Paint.valueOf("#276c93"));
            qty = true;
        }else{
            txtQty.setUnFocusColor(Paint.valueOf("red"));
            qty = false;
        }
        if(txtHeight.getText().matches("\\d+\\.\\d+") || txtHeight.getText().matches("[0-9]*") ) {
            txtHeight.setUnFocusColor(Paint.valueOf("#276c93"));
            height = true;
        }else{
            txtHeight.setUnFocusColor(Paint.valueOf("red"));
            height = false;
        }
        if(txtLength.getText().matches("\\d+\\.\\d+") || txtLength.getText().matches("[0-9]*")) {
            txtLength.setUnFocusColor(Paint.valueOf("#276c93"));
            length = true;
        }else{
            txtLength.setUnFocusColor(Paint.valueOf("red"));
            length = false;
        }
        if(txtWidth.getText().matches("\\d+\\.\\d+") || txtWidth.getText().matches("[0-9]*")) {
            txtWidth.setUnFocusColor(Paint.valueOf("#276c93"));
            width = true;
        }else{
            txtWidth.setUnFocusColor(Paint.valueOf("red"));
            width = false;
        }
        if(qty==true && width==true && height==true && length==true){
            return true;
        }else {
            return false;
        }
    }

    public void cmbLogIdOnAction(ActionEvent actionEvent) {
       logId = (String) cmbLogId.getValue();
        try {
            String type = StockModel.getType(logId);
            lblType.setText(type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        List<Stock> stockList = new ArrayList<>();

        for (int i = 0; i < tblWoodcut.getItems().size(); i++) {
            Stock tm = obList.get(i);

            Stock stock = new Stock(tm.getWoodcutId(),tm.getType(),tm.getLength(),tm.getWidth(),tm.getHeight(),tm.getQty(),tm.getLogId());
            stockList.add(stock);
        }

        try {
            boolean isWoodcutTableUpdated = StockModel.addWoodcutDetails(stockList);
            if (isWoodcutTableUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Added successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Couldn't add!").show();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLogId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colWoodcutId.setCellValueFactory(new PropertyValueFactory<>("woodcutId"));
        colHeigth.setCellValueFactory(new PropertyValueFactory<>("height"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colLogId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        colWidth.setCellValueFactory(new PropertyValueFactory<>("width"));
    }

    private void loadLogId() {
        try {
            List<String> ids = StockModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbLogId.setItems(obList);
            // cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }
}
