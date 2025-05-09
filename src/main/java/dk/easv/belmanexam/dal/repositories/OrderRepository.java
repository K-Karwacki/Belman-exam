package dk.easv.belmanexam.dal.repositories;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.bll.utils.Status;

import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order>{
    Optional<Order> getByOrderNumber(String orderNumber);
}
