package service.custom;

import javafx.collections.ObservableList;
import model.Product;
import service.SuperService;

import java.util.List;

public interface ProductService extends SuperService {
    boolean addProduct(Product product);
    ObservableList<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(String id);

    ObservableList<Product> findProductsBySupplierID(String supplierId);

    Product findProductByProductID(String productId);
}
