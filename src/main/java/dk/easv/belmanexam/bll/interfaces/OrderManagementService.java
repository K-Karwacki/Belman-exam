package dk.easv.belmanexam.bll.interfaces;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.ui.models.OrderModel;
import dk.easv.belmanexam.ui.models.lists.OrderListModel;

import java.util.Optional;

public interface OrderManagementService {
    OrderListModel getOrderListModel();
    void updateOrder(OrderModel orderModel);
    Optional<Order> getByOrderNumber(String orderNumber);
}
