package dk.easv.belmanexam.services.implementations;

import dk.easv.belmanexam.services.factories.RepositoryService;
import dk.easv.belmanexam.services.interfaces.OrderManagementService;
import dk.easv.belmanexam.repositories.interfaces.OrderRepository;
import dk.easv.belmanexam.ui.models.OrderListModel;

import java.util.ArrayList;

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
//        orderListModel.setOrders(new ArrayList<>());
    }

    public OrderListModel getOrderListModel() {
        return orderListModel;
    }
}
