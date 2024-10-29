package repository.custom;

import entity.CustomerEntity;
import repository.SuperRepository;

import java.util.List;

public interface CustomerRepository extends SuperRepository {
    List<CustomerEntity> findAll();
}
