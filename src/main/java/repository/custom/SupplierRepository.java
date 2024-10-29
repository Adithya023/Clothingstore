package repository.custom;

import entity.SupplierEntity;
import repository.SuperRepository;

import java.util.List;

public interface SupplierRepository extends SuperRepository {
    List<SupplierEntity> findAll();

    SupplierEntity findBySupplierID(String supplierId);

    SupplierEntity findByName(String name);

    void deleteByID(String supplierId);

    void update(SupplierEntity supplierEntity);

    void save(SupplierEntity supplierEntity);
}
