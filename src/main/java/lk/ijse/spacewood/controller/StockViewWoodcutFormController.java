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
import lk.ijse.spacewood.dto.Log;
import lk.ijse.spacewood.dto.OrderDetailDTO;
import lk.ijse.spacewood.dto.Stock;
import lk.ijse.spacewood.dto.tm.ViewOrderTM;
import lk.ijse.spacewood.model.OrderModel;
import lk.ijse.spacewood.model.StockModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.loadType;


public class StockViewWoodcutFormController implements Initializable {
    public JFXComboBox cmbLogId;
    public JFXTextField txtWoodcutId;
    public JFXTextField txtQty;
    public JFXComboBox cmbType;
    public Label lblMahogany;
    public Label lblJak;
    public Label lblTeak;
    public Label lblSooriyamara;
    public Label lblKumbuk;
    public TableView tblWoodcut;
    public TableColumn colWoodcutId;
    public TableColumn colType;
    public TableColumn colLength;
    public TableColumn colWidth;
    public TableColumn colHeight;
    public TableColumn colQty;
    public TableColumn colLogId;

    private ObservableList<Stock> obList = FXCollections.observableArrayList();
    public void cmbLogIdOnAction(ActionEvent actionEvent) {
        obList.clear();
        String logId = (String) cmbLogId.getValue();
        obList = FXCollections.observableArrayList();
        try {
            List<Stock> stockList = StockModel.getDetailFromLogId(logId);
            for(Stock stock : stockList) {
                obList.add(new Stock(
                        stock.getWoodcutId(),
                        stock.getType(),
                        stock.getLength(),
                        stock.getWidth(),
                        stock.getHeight(),
                        stock.getQty(),
                        stock.getLogId()
                ));
                //cmbType.setValue(stock.getType());
            }
            tblWoodcut.setItems(obList);
           // cmbType.setValue("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if (check) {
            String woodcutId = txtWoodcutId.getText();
            int qtyUsed = Integer.parseInt(txtQty.getText());


            int i = 0;
            if (!obList.isEmpty()) {
                for (i = 0; i < tblWoodcut.getItems().size(); i++) {
                    if (colWoodcutId.getCellData(i).equals(woodcutId)) {
                        int remainingWoodcuts = obList.get(i).getQty() - qtyUsed;

                        obList.get(i).setQty(remainingWoodcuts);

                        tblWoodcut.refresh();

                        return;
                    }
                }
                new Alert(Alert.AlertType.ERROR, "No such woodcuts!").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "No such woodcuts!").show();
            }


        }
    }

    private boolean checkValidation() {
        boolean qty;
        if(txtQty.getText().matches("[0-9]*")) {
            txtQty.setUnFocusColor(Paint.valueOf("#276c93"));
            qty = true;
        }else{
            txtQty.setUnFocusColor(Paint.valueOf("red"));
            qty = false;
        }
        if( qty==true){
            return true;
        }else {
            return false;
        }
    }

    public void cmbTypeOnAction(ActionEvent actionEvent) {
        obList.clear();
        String type = (String) cmbType.getValue();
        obList = FXCollections.observableArrayList();
        try {
            List<Stock> stockList = StockModel.getDetailFromType(type);
            for(Stock stock : stockList) {
                obList.add(new Stock(
                        stock.getWoodcutId(),
                        stock.getType(),
                        stock.getLength(),
                        stock.getWidth(),
                        stock.getHeight(),
                        stock.getQty(),
                        stock.getLogId()
                ));
            }
            tblWoodcut.setItems(obList);
           // cmbLogId.setValue("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLogIds();
        loadLogType();
        setLabels();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colLogId.setCellValueFactory(new PropertyValueFactory<>("logId"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colWidth.setCellValueFactory(new PropertyValueFactory<>("width"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colWoodcutId.setCellValueFactory(new PropertyValueFactory<>("woodcutId"));
    }

    private void setLabels() {
        String[] names = {"Mahogany", "Jak", "Teak", "Sooriyamara", "Kumbuk"};
        int[] count = new int[5];
        for (int i = 0; i < names.length; i++) {
            try {
                count[i] =StockModel.getWoodcutCount(names[i]);
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

    private void loadLogType() {
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

    private void loadLogIds() {
        try {
            List<String> ids = StockModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbLogId.setItems(obList);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        List<Stock> stockList = new ArrayList<>();

        for (int i = 0; i < tblWoodcut.getItems().size(); i++) {
            Stock tm = obList.get(i);

            Stock stock = new Stock(tm.getWoodcutId(),tm.getType(), tm.getLength(), tm.getWidth(), tm.getHeight(),tm.getQty(), tm.getLogId());
            stockList.add(stock);
        }
        try {
            boolean isWoodcutTableUpdated = StockModel.updateWoodcutDetailsAgain(stockList);
            if(isWoodcutTableUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully!").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Couldn't update!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
