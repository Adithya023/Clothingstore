package service.custom;

import javafx.collections.ObservableList;
import model.OrderItems;
import service.SuperService;

public interface OrderItemsService extends SuperService {
    ObservableList<OrderItems> findOrderItemsByOrderID(String orderId);

    void saveOrder(OrderItems orderItem);
}
