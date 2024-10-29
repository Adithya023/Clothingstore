package service.custom.impl;

import entity.CustomerEntity;
import entity.OrderEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Order;
import repository.RepositoryFactory;
import repository.custom.OrderRepository;
import service.custom.OrderService;
import util.Type;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderItemsRepository= RepositoryFactory.getInstance().getRepositoryType(Type.ORDER);

    @Override
    public ObservableList<Order> getAllOrders() {
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        List<OrderEntity> orderEntityList = orderItemsRepository.findAll();

        orderEntityList.forEach(orderEntity -> {
            CustomerEntity customerEntity = orderEntity.getCustomerEntity();
            orderObservableList.add(
                    new Order(
                            orderEntity.getOrderId(),
                            orderEntity.getDate(),
                            orderEntity.getTime(),
                            orderEntity.getTotal(),
                            orderEntity.getPaymentType(),
                            new Customer(customerEntity.getCustomerId(), customerEntity.getName(), customerEntity.getEmail(), customerEntity.getPhoneNumber()),
                            orderEntity.getOrderItemCount())
            );
        });
        return orderObservableList;
    }
}
