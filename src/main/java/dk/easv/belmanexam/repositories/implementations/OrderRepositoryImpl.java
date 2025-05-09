package dk.easv.belmanexam.repositories.implementations;

import dk.easv.belmanexam.model.Order;
import dk.easv.belmanexam.repositories.interfaces.OrderRepository;

import java.util.Collection;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    @Override public Collection<Order> getAll()
    {
        return null;
    }

    @Override public Optional<Order> getById(long id)
    {
        return Optional.empty();
    }

    @Override public Order add(Order entity)
    {
        return null;
    }

    @Override public boolean delete(Order entity)
    {
        return false;
    }

    @Override public Order update(Order newEntity)
    {
        return null;
    }
}
