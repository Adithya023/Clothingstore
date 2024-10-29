package repository.custom;

import entity.OrderEntity;
import repository.SuperRepository;

import java.util.List;

public interface OrderRepository extends SuperRepository {
    List<OrderEntity> findAll();
}
