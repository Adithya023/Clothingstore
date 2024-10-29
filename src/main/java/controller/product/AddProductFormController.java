package controller.product;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Setter;
import model.Category;
import model.Product;
import model.Supplier;
import service.ServiceFactory;
import service.custom.CategoryService;
import service.custom.ProductService;
import service.custom.SupplierService;
import util.Type;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class AddProductFormController implements Initializable {
    private EmployeeDashboardFormController employeeDashboardFormController;

    private final SupplierService supplierService = ServiceFactory.getInstance().getServiceType(Type.SUPPLIER);
    private final ProductService productService = ServiceFactory.getInstance().getServiceType(Type.PRODUCT);
    private final CategoryService categoryService = ServiceFactory.getInstance().getServiceType(Type.CATEGORY);

    @FXML
    private JFXComboBox<String> cmbSetProductSupplier;

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
            String name = txtSetProductName.getText().trim();
            String category = txtSetProductCategory.getText().trim();
            Double unitPrice = Double.parseDouble(txtSetProductUnitPrice.getText().trim());
            String supplier = cmbSetProductSupplier.getValue().trim();
            Integer quantity = spinnerSetProductQuantity.getValue();
            if(!name.equals("") && !category.equals("") && unitPrice!=null){
                Supplier supplierObject = new Supplier();
                if(!supplier.equals("")){
                    supplierObject = supplierService.findSupplierByName(supplier);
                }

                Category categoryObject = categoryService.findCategoryByName(category);
                if (categoryObject.getCategoryId()==null){
                    categoryObject = new Category();
                    categoryObject.setName(category);
                }

                productService.addProduct(new Product(null,name,categoryObject,quantity,unitPrice,supplierObject));
                employeeDashboardFormController.loadCatalogProductsTable(productService.getAllProducts());
                employeeDashboardFormController.setLabels();
                btnCancelAddProductsOnAction(event);
                return;
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Please Enter All the Fields with Correct Data");
            alert.show();
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

    @FXML
    void btnRemoveSupplierOnAction(ActionEvent event) {
        cmbSetProductSupplier.setValue("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Supplier> supplierObservableList=supplierService.getAllSuppliers();
        ObservableList<String> b= FXCollections.observableArrayList();
        supplierObservableList.forEach(supplier -> b.add(supplier.getName()));
        cmbSetProductSupplier.setItems(b);

        spinnerSetProductQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,750,1));
        cmbSetProductSupplier.setValue("");
    }
}
