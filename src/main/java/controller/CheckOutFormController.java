package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.employee.EmployeeDashboardFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lombok.Setter;
import model.OrderItems;
import model.Product;
import model.TempOrderItems;
import service.ServiceFactory;
import service.custom.OrderItemsService;
import service.custom.OrderService;
import service.custom.ProductService;
import util.Type;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Setter
public class CheckOutFormController implements Initializable {
    private final String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final String phoneNumberPattern = "^(\\+\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$";
    private final ProductService productService= ServiceFactory.getInstance().getServiceType(Type.PRODUCT);
    private final OrderItemsService orderItemsService= ServiceFactory.getInstance().getServiceType(Type.ORDERITEMS);
    private final OrderService orderService= ServiceFactory.getInstance().getServiceType(Type.ORDER);
    private Button checkOutButton;
    private EmployeeDashboardFormController employeeDashboardFormController;

    @FXML
    private JFXButton btnConfirmOrder;

    @FXML
    private JFXComboBox<String> cmbSetPaymentType;

    @FXML
    private TableColumn<OrderItems, Void> columnOrdersAction;

    @FXML
    private TableColumn<OrderItems, String> columnOrdersProductCategory;

    @FXML
    private TableColumn<OrderItems, String> columnOrdersProductID;

    @FXML
    private TableColumn<OrderItems, String> columnOrdersProductName;

    @FXML
    private TableColumn<OrderItems, String> columnOrdersProductSize;

    @FXML
    private TableColumn<OrderItems, String> columnOrdersProductSupplier;

    @FXML
    private TableColumn<OrderItems, Double> columnOrdersUnitPrice;

    @FXML
    private TableView<OrderItems> tblOrderItems;

    @FXML
    private Label lblTotalCost;

    @FXML
    private JFXTextField txtSetCustomerEmail;

    @FXML
    private JFXTextField txtSetCustomerName;

    @FXML
    private JFXTextField txtSetCustomerPhoneNumber;

    @FXML
    void btnClearOrderOnAction(ActionEvent event) {
        EmployeeDashboardFormController.tempOrderItemsList.clear();
        checkOutButton.setDisable(true);
        employeeDashboardFormController.loadCatalogProductsTable(productService.getAllProducts());
        btnCloseCheckOutOnAction(event);
    }

    @FXML
    void btnConfirmOrderOnAction(ActionEvent event) {
        String name = txtSetCustomerName.getText().trim();
        String email = txtSetCustomerEmail.getText().trim();
        String phoneNumber = txtSetCustomerPhoneNumber.getText().trim();
        ObservableList<OrderItems> orderItems = tblOrderItems.getItems();
        if(!name.equals("") && isValidEmail(email) && isValidPhoneNumber(phoneNumber) && orderItems.size()!=0 && !cmbSetPaymentType.getValue().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Order Confirmation");
            alert.setHeaderText("Confirm Order");
            alert.setContentText("Do you want to confirm the order?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now().withNano(0);
                orderItems.forEach(orderItem -> {
                    orderItem.getOrder().setDate(date);
                    orderItem.getOrder().setTime(time);
                    orderItem.getOrder().setPaymentType(cmbSetPaymentType.getValue());
                    orderItem.getOrder().getCustomer().setName(name);
                    orderItem.getOrder().getCustomer().setEmail(email);
                    orderItem.getOrder().getCustomer().setPhoneNumber(phoneNumber);
                    orderItem.getOrder().setOrderItemCount(tblOrderItems.getItems().size());
                    orderItemsService.saveOrder(orderItem);
                });

                EmployeeDashboardFormController.tempOrderItemsList.forEach(tempOrderItems -> {
                    Product product = productService.findProductByProductID(tempOrderItems.getOrderItem().getProductId());
                    product.setQuantity(product.getQuantity()-tempOrderItems.getQuantity());
                    productService.updateProduct(product);
                });

                employeeDashboardFormController.loadCatalogProductsTable(productService.getAllProducts());
                employeeDashboardFormController.loadOrdersTable(orderService.getAllOrders());
                employeeDashboardFormController.setLabels();

                Alert completionAlert = new Alert(Alert.AlertType.INFORMATION);
                completionAlert.setTitle("Confirmation Successful");
                completionAlert.setHeaderText(null);
                completionAlert.setContentText("Order Has Been Processed");
                completionAlert.showAndWait();
                btnClearOrderOnAction(event);
            }
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText("Please Enter All the Fields with Correct Data");
        alert.show();
    }

    @FXML
    void btnCloseCheckOutOnAction(ActionEvent event) {
        if(EmployeeDashboardFormController.tempOrderItemsList.size()==0){
            checkOutButton.setDisable(true);
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Scene scene = EmployeeDashboardFormController.employeeDashboardStage.getScene();
        AnchorPane root = (AnchorPane) scene.getRoot();
        VBox vbox = (VBox) root.getChildren().get(7);
        vbox.setVisible(false);
        vbox.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirmOrder.setDisable(false);
        setPaymentTypes();
        loadOrderItemsTable(EmployeeDashboardFormController.tempOrderItemsList);
        calculateTotal();
    }

    private void setPaymentTypes() {
        ObservableList<String> paymentTypeList = FXCollections.observableArrayList();
        paymentTypeList.add("Cash");
        paymentTypeList.add("Card");
        paymentTypeList.add("CryptoCurrency");
        cmbSetPaymentType.setValue("");
        cmbSetPaymentType.setItems(paymentTypeList);
    }

    private void calculateTotal() {
        Double total=0.0;
        List<TempOrderItems> tempOrderItemsList = EmployeeDashboardFormController.tempOrderItemsList;
        for (int index=0;tempOrderItemsList.size()>index;index++){
            total+=(tempOrderItemsList.get(index).getOrderItem().getUnitPrice()* tempOrderItemsList.get(index).getQuantity());
        }
        lblTotalCost.setText("Rs. "+total);
    }

    private void loadOrderItemsTable(List<TempOrderItems> tempOrderItemsList) {
        if (tempOrderItemsList.size()!=0){
            ObservableList<OrderItems> orderItems = FXCollections.observableArrayList();
            tempOrderItemsList.forEach(tempOrderItems -> {
                for (int index=0;tempOrderItems.getQuantity()>index;index++) orderItems.add(tempOrderItems.getOrderItem());
            });

            columnOrdersProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            columnOrdersProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            columnOrdersProductCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
            columnOrdersProductSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
            columnOrdersUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            columnOrdersProductSize.setCellValueFactory(new PropertyValueFactory<>("size"));

            columnOrdersProductID.setStyle("-fx-alignment:center;");
            columnOrdersProductName.setStyle("-fx-alignment:center;");
            columnOrdersProductCategory.setStyle("-fx-alignment:center;");
            columnOrdersProductSupplier.setStyle("-fx-alignment:center;");
            columnOrdersUnitPrice.setStyle("-fx-alignment:center;");
            columnOrdersProductSize.setStyle("-fx-alignment:center;");

            setActionsToTable();
            tblOrderItems.setItems(orderItems);
        }
    }

    private void setActionsToTable(){
        Callback<TableColumn<OrderItems, Void>, TableCell<OrderItems, Void>> orderItemsCellFactory = new Callback<>() {
            @Override
            public TableCell<OrderItems, Void> call(final TableColumn<OrderItems, Void> param) {
                return new TableCell<>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button removeIcon = new Button("Remove");
                            removeIcon.setStyle("-fx-cursor: hand ;");

                            removeIcon.setOnMouseClicked(event -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setHeaderText("Are you sure you want to delete the selected Item?");
                                alert.setContentText("Please confirm your action.");
                                Optional<ButtonType> result = alert.showAndWait();

                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    Alert completionAlert = new Alert(Alert.AlertType.INFORMATION);
                                    completionAlert.setTitle("Deletion Successful");
                                    completionAlert.setHeaderText(null);
                                    completionAlert.setContentText("Selected property has been successfully deleted.");
                                    completionAlert.showAndWait();

                                    OrderItems orderItems = tblOrderItems.getItems().get(getIndex());
                                    System.out.println(orderItems);

                                    for (int index=0;EmployeeDashboardFormController.tempOrderItemsList.size()>index;index++){
                                        boolean isProductIdMatches = orderItems.getProductId().equals(EmployeeDashboardFormController.tempOrderItemsList.get(index).getOrderItem().getProductId());
                                        boolean isSizeMatches = orderItems.getSize().equals(EmployeeDashboardFormController.tempOrderItemsList.get(index).getOrderItem().getSize());
                                        if (isProductIdMatches && isSizeMatches){
                                            if(EmployeeDashboardFormController.tempOrderItemsList.get(index).getQuantity()>1){
                                                EmployeeDashboardFormController.tempOrderItemsList.get(index).setQuantity(EmployeeDashboardFormController.tempOrderItemsList.get(index).getQuantity()-1);
                                            }else{
                                                EmployeeDashboardFormController.tempOrderItemsList.remove(index);
                                            }

                                            if (EmployeeDashboardFormController.tempOrderItemsList.size()==0){
                                                tblOrderItems.getItems().clear();
                                                btnConfirmOrder.setDisable(true);
                                            }
                                            for(int i=0;EmployeeDashboardFormController.allProducts.size()>i;i++){
                                                if(EmployeeDashboardFormController.allProducts.get(i).getProductId().equals(orderItems.getProductId())){
                                                    EmployeeDashboardFormController.allProducts.get(i).setQuantity(EmployeeDashboardFormController.allProducts.get(i).getQuantity()+1);
                                                }
                                            }

                                            loadOrderItemsTable(EmployeeDashboardFormController.tempOrderItemsList);
                                            employeeDashboardFormController.loadCatalogProductsTable(EmployeeDashboardFormController.allProducts);
                                            calculateTotal();
                                            return;
                                        }
                                    }
                                }
                            });

                            HBox managebtn = new HBox(removeIcon);
                            managebtn.setStyle("-fx-alignment:center");
                            managebtn.setSpacing(8);
                            setGraphic(managebtn);
                        }
                    }
                };
            }
        };
        columnOrdersAction.setCellFactory(orderItemsCellFactory);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(phoneNumberPattern);
    }

    private boolean isValidEmail(String email) {
        return email.matches(emailPattern);
    }
}
