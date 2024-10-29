package repository.custom;

import entity.ProductEntity;
import repository.SuperRepository;

import java.util.List;

public interface ProductRepository extends SuperRepository {
    boolean save(ProductEntity entity);
    void update(ProductEntity entity);
    List<ProductEntity> findAll();

    ProductEntity findByID(String productId);

    void deleteByID(String productId);

    List<ProductEntity> findBySupplierID(String supplierId);
}
