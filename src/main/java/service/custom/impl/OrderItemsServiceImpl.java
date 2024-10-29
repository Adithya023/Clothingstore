package service.custom.impl;

import entity.CustomerEntity;
import entity.OrderEntity;
import entity.OrderItemsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Order;
import model.OrderItems;
import repository.RepositoryFactory;
import repository.custom.OrderItemsRepository;
import service.custom.OrderItemsService;
import util.Type;

import java.util.List;

public class OrderItemsServiceImpl implements OrderItemsService {
    private final OrderItemsRepository orderItemsRepository= RepositoryFactory.getInstance().getRepositoryType(Type.ORDERITEMS);

    @Override
    public ObservableList<OrderItems> findOrderItemsByOrderID(String orderId) {
        ObservableList<OrderItems> orderItemsObservableList = FXCollections.observableArrayList();
        List<OrderItemsEntity> orderItemsEntityList = orderItemsRepository.findByOrderID(orderId);

        orderItemsEntityList.forEach(orderItemsEntity -> {
            OrderEntity orderEntity = orderItemsEntity.getOrderEntity();
            CustomerEntity customerEntity = orderEntity.getCustomerEntity();

            Customer customer = new Customer(customerEntity.getCustomerId(), customerEntity.getName(), customerEntity.getEmail(), customerEntity.getPhoneNumber());
            Order order = new Order(
                    orderEntity.getOrderId(),
                    orderEntity.getDate(),
                    orderEntity.getTime(),
                    orderEntity.getTotal(),
                    orderEntity.getPaymentType(),
                    customer,
                    orderEntity.getOrderItemCount());

            OrderItems orderItem = new OrderItems(
                    order,
                    orderItemsEntity.getProductName(),
                    orderItemsEntity.getProductId(),
                    orderItemsEntity.getCategoryId(),
                    orderItemsEntity.getCategoryName(),
                    orderItemsEntity.getSupplierId(),
                    orderItemsEntity.getSupplierName(),
                    orderItemsEntity.getUnitPrice(),
                    orderItemsEntity.getSize()
            );
            orderItemsObservableList.add(orderItem);
        });
        return orderItemsObservableList;
    }

    @Override
    public void saveOrder(OrderItems orderItem) {
        OrderItemsEntity  orderItemsEntity = new OrderItemsEntity();

        orderItemsEntity.setOrderEntity(new OrderEntity(null,null, orderItem.getOrder().getDate(), orderItem.getOrder().getTime(), orderItem.getOrder().getTotal(), orderItem.getOrder().getPaymentType(), orderItem.getOrder().getOrderItemCount(), new CustomerEntity(null,null,orderItem.getOrder().getCustomer().getName(),orderItem.getOrder().getCustomer().getEmail(),orderItem.getOrder().getCustomer().getPhoneNumber())));
        orderItemsEntity.setProductName(orderItem.getProductName());
        orderItemsEntity.setProductId(orderItem.getProductId());
        orderItemsEntity.setCategoryId(orderItem.getCategoryId());
        orderItemsEntity.setCategoryName(orderItem.getCategoryName());
        orderItemsEntity.setSupplierId(orderItem.getSupplierId());
        orderItemsEntity.setSupplierName(orderItem.getSupplierName());
        orderItemsEntity.setSize(orderItem.getSize());
        orderItemsEntity.setUnitPrice(orderItem.getUnitPrice());

        orderItemsRepository.save(orderItemsEntity);
    }
}
