package service.custom.impl;

import entity.CategoryEntity;
import entity.ProductEntity;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Product;
import model.Supplier;
import repository.RepositoryFactory;
import repository.custom.CategoryRepository;
import repository.custom.ProductRepository;
import repository.custom.SupplierRepository;
import service.custom.ProductService;
import util.Type;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository = RepositoryFactory.getInstance().getRepositoryType(Type.PRODUCT);
    private final CategoryRepository categoryRepository = RepositoryFactory.getInstance().getRepositoryType(Type.CATEGORY);
    private final SupplierRepository supplierRepository = RepositoryFactory.getInstance().getRepositoryType(Type.SUPPLIER);

    @Override
    public boolean addProduct(Product product) {
        ProductEntity entity = new ProductEntity(null,null,product.getName(),new CategoryEntity(), product.getQuantity(), product.getUnitPrice(), new SupplierEntity());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(product.getCategory().getName());
        categoryEntity.setCategoryId(product.getCategory().getCategoryId());
        entity.setCategoryEntity(categoryEntity);

        Supplier supplier = product.getSupplier();
        if(supplier!=null) {
            SupplierEntity supplierEntity = new SupplierEntity();
            supplierEntity.setName(supplier.getName());
            supplierEntity.setEmail(supplier.getEmail());
            supplierEntity.setCompany(supplier.getCompany());
            supplierEntity.setSupplierId(supplier.getSupplierId());

            entity.setSupplierEntity(supplierEntity);
        }
        return productRepository.save(entity);
    }

    @Override
    public ObservableList<Product> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        ObservableList<Product> productObservableList= FXCollections.observableArrayList();
        if (productEntityList!=null){
            productEntityList.forEach(entity->{
                Product product = new Product(entity.getProductId(), entity.getName(), new Category(),entity.getQuantity(), entity.getUnitPrice(), new Supplier());
                if (entity.getCategoryEntity()!=null) product.setCategory(new Category(entity.getCategoryEntity().getCategoryId(),entity.getCategoryEntity().getName()));
                if (entity.getSupplierEntity()!=null) product.setSupplier(new Supplier(entity.getSupplierEntity().getSupplierId(),entity.getSupplierEntity().getName(),entity.getSupplierEntity().getCompany(),entity.getSupplierEntity().getEmail()));
                productObservableList.add(product);
            });
        }
        return productObservableList;
    }

    @Override
    public void updateProduct(Product product) {
        ProductEntity productEntity = productRepository.findByID(product.getProductId());
        productEntity.setName(product.getName());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setUnitPrice(product.getUnitPrice());

        productEntity.setCategoryEntity(categoryRepository.findByCategoryID(product.getCategory().getCategoryId()));
        if (product.getSupplier()!=null){
            productEntity.setSupplierEntity(supplierRepository.findBySupplierID(product.getSupplier().getSupplierId()));
        }else{
            productEntity.setSupplierEntity(null);
        }
        productRepository.update(productEntity);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteByID(productId);
    }

    @Override
    public ObservableList<Product> findProductsBySupplierID(String supplierId) {
        List<ProductEntity> suppliedProducts = productRepository.findBySupplierID(supplierId);
        ObservableList<Product> productObservableList= FXCollections.observableArrayList();
        if (suppliedProducts!=null){
            suppliedProducts.forEach(entity->{
                Product product = new Product(entity.getProductId(), entity.getName(), new Category(),entity.getQuantity(), entity.getUnitPrice(), new Supplier());
                if (entity.getCategoryEntity()!=null) product.setCategory(new Category(entity.getCategoryEntity().getCategoryId(),entity.getCategoryEntity().getName()));
                if (entity.getSupplierEntity()!=null) product.setSupplier(new Supplier(entity.getSupplierEntity().getSupplierId(),entity.getSupplierEntity().getName(),entity.getSupplierEntity().getCompany(),entity.getSupplierEntity().getEmail()));
                productObservableList.add(product);
            });
        }
        return productObservableList;
    }

    @Override
    public Product findProductByProductID(String productId) {
        ProductEntity productEntity = productRepository.findByID(productId);
        return new Product(
                productEntity.getProductId(),
                productEntity.getName(),
                new Category(productEntity.getCategoryEntity().getCategoryId(),productEntity.getCategoryEntity().getName()),
                productEntity.getQuantity(),
                productEntity.getUnitPrice(),
                new Supplier(productEntity.getSupplierEntity().getSupplierId(),productEntity.getSupplierEntity().getName(),productEntity.getSupplierEntity().getCompany(),productEntity.getSupplierEntity().getEmail())
        );
    }
}
