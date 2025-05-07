package dk.easv.belmanexam.bll.implementations;

import dk.easv.belmanexam.bll.factories.RepositoryService;
import dk.easv.belmanexam.bll.interfaces.OrderManagementService;
import dk.easv.belmanexam.dal.repositories.OrderRepository;
import dk.easv.belmanexam.ui.models.OrderListModel;

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
        orderListModel.setOrders(orderRepository.getAll());
    }

    public OrderListModel getOrderListModel() {
        return orderListModel;
    }
}
