package service.custom;

import javafx.collections.ObservableList;
import model.Order;
import model.OrderItems;
import service.SuperService;

public interface OrderService extends SuperService {
    ObservableList<Order> getAllOrders();
}
