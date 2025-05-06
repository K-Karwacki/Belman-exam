package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.be.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderListModel {
    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    public OrderListModel() {

    }
}
