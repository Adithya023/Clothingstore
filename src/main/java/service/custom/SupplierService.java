package service.custom;

import javafx.collections.ObservableList;
import model.Product;
import model.Supplier;
import service.SuperService;

public interface SupplierService extends SuperService {
    ObservableList<Supplier> getAllSuppliers();

    Supplier findSupplierByName(String value);

    void deleteSupplier(String supplierId);

    void updateSupplier(Supplier supplier);

    void addSupplier(Supplier supplier);
}
