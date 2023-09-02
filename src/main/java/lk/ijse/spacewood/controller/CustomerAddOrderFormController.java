package lk.ijse.spacewood.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.ijse.spacewood.db.DBConnection;
import lk.ijse.spacewood.dto.Customer;
import lk.ijse.spacewood.dto.Item;
import lk.ijse.spacewood.dto.OrderDTO;
import lk.ijse.spacewood.dto.OrderDetails;
import lk.ijse.spacewood.dto.tm.AddOrderTM;
import lk.ijse.spacewood.model.CustomerModel;
import lk.ijse.spacewood.model.ItemModel;
import lk.ijse.spacewood.model.OrderModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CustomerAddOrderFormController extends Application implements Initializable {

    public Label lblOrderId;
    public Label lblDate;
    public  JFXComboBox cmbCustName;
    public  JFXComboBox cmbCustId;
    public JFXComboBox cmbItem;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtQty;
    public double unitPrice;
    public TableView<AddOrderTM> tblAddOrder;
    public TableColumn<?, ?> colItemCode;
    public TableColumn<?, ?> colDescription;
    public TableColumn<?, ?> colQty;
    public TableColumn<?, ?> colUnitPrice;
    public TableColumn<?, ?> colTotal;
    public TableColumn<?, ?> colAction;
    public Label lblTotalCost;
    public JFXButton btnReport;
    private ObservableList<AddOrderTM> obList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextOrderID();
        setOrderDate();
        loadCustomerIDs();
        loadCustomerNames();
        loadItemCode();
        loadItemName();
       setCellValueFactory();
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadItemName() {
        try {
            List<String> names = OrderModel.getItemNames();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String name : names) {
                obList.add(name);
            }
            cmbItem.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadItemCode() {
        try {
            List<String> codes = OrderModel.getCodes();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String code : codes) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadCustomerNames() {
        try {
            List<String> names = CustomerModel.getNames();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String name : names) {
                obList.add(name);
            }
            cmbCustName.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void loadCustomerIDs() {
        try {
            List<String> ids = CustomerModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbCustId.setItems(obList);
            cmbCustId.setValue("C001");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            throw new RuntimeException(e);

        }
    }

    private void setOrderDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextOrderID() {
        try {
            String nextID = OrderModel.nextOrderID();
            lblOrderId.setText(nextID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustNameOnAction(ActionEvent actionEvent) {
        String name = (String) cmbCustName.getSelectionModel().getSelectedItem();
        try {
            Customer customer = CustomerModel.searchByName(name);
            cmbCustId.setValue(customer.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbCustId.getSelectionModel().getSelectedItem();

        try {
            Customer customer = CustomerModel.searchById(id);
           cmbCustName.setValue(customer.getFirst_name());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbItemOnAction(ActionEvent actionEvent) {
        String name = (String) cmbItem.getSelectionModel().getSelectedItem();

        try {
            Item item = ItemModel.searchByItem(name);
            cmbItemCode.setValue(item.getItemCode());
            unitPrice = item.getUnitPrice();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        String name = (String) cmbItemCode.getSelectionModel().getSelectedItem();

        try {
            Item item = ItemModel.searchByItemCode(name);
            cmbItem.setValue(item.getDescription());
            unitPrice = item.getUnitPrice();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) {
        boolean check = checkValidation();
        if(check) {
            String itemDescription = (String) cmbItem.getValue();
            String itemCode = (String) cmbItemCode.getValue();
            int qty = Integer.parseInt(txtQty.getText());
            double total = qty * unitPrice;
            Button btnRemove = new Button("Remove");
            btnRemove.setCursor(Cursor.HAND);

            setRemoveBtnOnAction(btnRemove);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblAddOrder.getItems().size(); i++) {
                    if (colItemCode.getCellData(i).equals(itemCode)) {
                        qty += (int) colQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTotal(total);

                        tblAddOrder.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            AddOrderTM tm = new AddOrderTM(itemCode, itemDescription, qty, unitPrice, total, btnRemove);

            obList.add(tm);
            tblAddOrder.setItems(obList);
            calculateNetTotal();

            txtQty.setText("");
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


        if(qty==true ){
            return true;
        }else {
            return false;
        }
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblAddOrder.getItems().size(); i++) {
            double total  = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblTotalCost.setText(String.valueOf(netTotal));
    }

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblAddOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                tblAddOrder.refresh();
                calculateNetTotal();
            }

        });
    }

    public void btnSaveOnAction(ActionEvent actionEvent)  {
       // Connection connection = DBConnection.getInstance().getConnection();
        String customerID = (String) cmbCustId.getValue();
        String orderID = lblOrderId.getText();
        String date = lblDate.getText();
        String totalCost = lblTotalCost.getText();

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (int i = 0; i < tblAddOrder.getItems().size(); i++) {
            AddOrderTM tm = obList.get(i);

            OrderDTO orderDTO = new OrderDTO(tm.getCode(), tm.getQty());
            orderDTOList.add(orderDTO);
        }
        try{
            boolean isTableUpdated = OrderModel.updateBothTables(customerID, orderID, date, totalCost,orderDTOList );
            if(isTableUpdated){
                 new Alert(Alert.AlertType.CONFIRMATION, "Order added successfully!").show();
                    btnReport.setDisable(false);
            }else {
                    new Alert(Alert.AlertType.ERROR, "Couldn't update!").show();
                }
        }catch(SQLException e){throw new RuntimeException(e);}

        /*try {
            boolean isOrderTableUpdated = OrderModel.updateOrderTable(customerID, orderID, date, totalCost);
            if (isOrderTableUpdated) {
                boolean isOrderDetailUpdated = OrderModel.updateOrderDetails(orderID, orderDTOList);
                if (isOrderDetailUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order added successfully!").show();
                    btnReport.setDisable(false);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Couldn't update!").show();
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }*/
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
        try {
            InputStream rpt = CustomerAddOrderFormController.class.getResourceAsStream("/jasper/MyReports/Blank_A4.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(rpt);

            Map<String,Object> data = new HashMap<>();
            data.put("orderId", lblOrderId.getText());
            data.put("name", cmbCustName.getValue());
            data.put("date", lblDate.getText());
            data.put("id", cmbCustId.getValue());
            data.put("cost", lblTotalCost.getText());

            JasperPrint filledReport = JasperFillManager.fillReport(compileReport,data, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(filledReport,false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddNewItemOnAction(ActionEvent actionEvent) throws IOException {
       /* AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/item_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add New Item");
        stage.centerOnScreen();*/
        try {
            start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/item_form.fxml"));

        stage.setTitle("Add New Item");
        stage.centerOnScreen();
        stage.setScene(new Scene(root));

        stage.show();
    }
}
