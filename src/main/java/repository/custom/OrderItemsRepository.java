package repository.custom;

import entity.OrderItemsEntity;
import model.OrderItems;
import repository.SuperRepository;

import java.util.List;

public interface OrderItemsRepository extends SuperRepository {
    List<OrderItemsEntity> findAll();

    List<OrderItemsEntity> findByOrderID(String orderId);

    void save(OrderItemsEntity orderItemsEntity);
}
