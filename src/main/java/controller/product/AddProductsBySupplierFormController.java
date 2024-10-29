package controller.product;

import com.jfoenix.controls.JFXTextField;
import controller.employee.EmployeeDashboardFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Category;
import model.Product;
import model.Supplier;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class AddProductsBySupplierFormController implements Initializable {
    private EmployeeDashboardFormController employeeDashboardFormController;

    @FXML
    private Spinner<Integer> spinnerSetProductQuantity;

    @FXML
    private JFXTextField txtSetProductCategory;

    @FXML
    private JFXTextField txtSetProductName;

    @FXML
    private JFXTextField txtSetProductUnitPrice;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        try{
            String name = txtSetProductName.getText();
            String categoryId = txtSetProductCategory.getText();
            Integer quantity = spinnerSetProductQuantity.getValue();
            Double unitPrice = Double.parseDouble(txtSetProductUnitPrice.getText());
            if(!name.equals("") && !categoryId.equals("") && unitPrice!=null){
                EmployeeDashboardFormController.suppliedProducts.add(new Product(null, name, new Category(null, categoryId), quantity, unitPrice, new Supplier()));
                System.out.println(EmployeeDashboardFormController.suppliedProducts);
                employeeDashboardFormController.loadSuppliersAddProductsTable();
                btnCancelAddProductsOnAction(event);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("Please Enter Data For All Fields");
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Please Enter Correct Data");
            alert.show();
        }
    }

    @FXML
    void btnCancelAddProductsOnAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Scene scene = EmployeeDashboardFormController.employeeDashboardStage.getScene();
        AnchorPane root = (AnchorPane) scene.getRoot();
        VBox vbox = (VBox) root.getChildren().get(7);
        vbox.setVisible(false);
        vbox.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpinnerInitialValues();
    }

    private void setSpinnerInitialValues() {
        spinnerSetProductQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,750,1));
    }
}
