package dk.easv.belmanexam.ui.models.lists;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.ui.models.OrderModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public class OrderListModel {
    private final ObservableList<OrderModel> orders = FXCollections.observableArrayList();

    public OrderListModel() {
    }

    public void setOrders(Collection<OrderModel> orders) {
        this.orders.clear();
        this.orders.setAll(orders);
    }

    public ObservableList<OrderModel> getOrders() {
        return orders;
    }

}
