package service.custom.impl;

import entity.ProductEntity;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Supplier;
import repository.RepositoryFactory;
import repository.custom.ProductRepository;
import repository.custom.SupplierRepository;
import service.custom.SupplierService;
import util.Type;

import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository = RepositoryFactory.getInstance().getRepositoryType(Type.SUPPLIER);
    private final ProductRepository productRepository = RepositoryFactory.getInstance().getRepositoryType(Type.PRODUCT);

    @Override
    public ObservableList<Supplier> getAllSuppliers() {
        List<SupplierEntity> supplierEntityList = supplierRepository.findAll();
        ObservableList<Supplier> supplierObservableList= FXCollections.observableArrayList();
        if (supplierEntityList!=null){
            supplierEntityList.forEach(entity->{
                supplierObservableList.add(new Supplier(entity.getSupplierId(),entity.getName(), entity.getCompany(), entity.getEmail()));
            });
        }
        return supplierObservableList;
    }

    @Override
    public Supplier findSupplierByName(String name) {
        SupplierEntity supplierEntity = supplierRepository.findByName(name);
        if(supplierEntity !=null){
            return new Supplier(supplierEntity.getSupplierId(), supplierEntity.getName(), supplierEntity.getCompany(), supplierEntity.getEmail());
        }
        return new Supplier();
    }

    @Override
    public void deleteSupplier(String supplierId) {
        List<ProductEntity> productsListBySupplierID = productRepository.findBySupplierID(supplierId);
        productsListBySupplierID.forEach(productEntity -> {
            productEntity.setSupplierEntity(null);
            productRepository.update(productEntity);
        });
        supplierRepository.deleteByID(supplierId);
        System.out.println(supplierId);
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        SupplierEntity supplierEntity = supplierRepository.findBySupplierID(supplier.getSupplierId());
        supplierEntity.setName(supplier.getName());
        supplierEntity.setCompany(supplier.getCompany());
        supplierEntity.setEmail(supplier.getEmail());
        supplierRepository.update(supplierEntity);
    }

    @Override
    public void addSupplier(Supplier supplier) {
        if(supplierRepository.findByName(supplier.getName())==null){
            supplierRepository.save(new SupplierEntity(null, null, supplier.getName(), supplier.getCompany(),supplier.getEmail()));
        }
    }
}
