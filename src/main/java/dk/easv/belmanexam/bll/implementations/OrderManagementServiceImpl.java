package dk.easv.belmanexam.bll.implementations;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.bll.factories.RepositoryService;
import dk.easv.belmanexam.bll.interfaces.OrderManagementService;
import dk.easv.belmanexam.dal.repositories.OrderRepository;
import dk.easv.belmanexam.ui.models.OrderModel;
import dk.easv.belmanexam.ui.models.lists.OrderListModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class OrderManagementServiceImpl implements OrderManagementService {
    private final RepositoryService repositoryService;
    private final OrderRepository orderRepository;
    private final OrderListModel orderListModel;

    public OrderManagementServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        this.orderRepository = repositoryService.getRepository(OrderRepository.class);
        this.orderListModel = new OrderListModel();
        initialize();
    }

    private void initialize(){
        if (orderListModel == null || orderRepository == null || repositoryService == null) {
            throw new RuntimeException("Load dependencies for OrderManagementService");
        }
        Collection<OrderModel> orderModels = new ArrayList<>();
        orderRepository.getAll().forEach(order -> {
            orderModels.add(new OrderModel(order));
        });
        orderListModel.setOrders(orderModels);
    }

    public OrderListModel getOrderListModel() {
        return orderListModel;
    }

    @Override
    public void updateOrder(OrderModel orderModel) {
        Optional<Order> order = getByOrderNumber(orderModel.getOrderNumber());
        if (order.isPresent()) {
            Order orderToUpdate = order.get();
            orderToUpdate.setStatus(orderModel.getStatus());
            orderToUpdate.setCustomer(orderModel.getCustomer());
            orderToUpdate.setDate(orderToUpdate.getDate());
            orderRepository.update(order.get());

            // Update the actual OrderModel
            orderListModel.getOrders()
                    .stream()
                    .filter(model -> model.getOrderNumber().equals(orderModel.getOrderNumber()))
                    .findFirst()
                    .ifPresent(existingModel -> {
                        existingModel.setStatus(orderModel.getStatus());
                        existingModel.setCustomer(orderModel.getCustomer());
                    });
        }
    }

    @Override
    public Optional<Order> getByOrderNumber(String orderNumber) {
        return orderRepository.getByOrderNumber(orderNumber);
    }


}
