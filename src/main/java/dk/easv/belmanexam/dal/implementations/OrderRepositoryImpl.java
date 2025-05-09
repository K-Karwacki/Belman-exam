package dk.easv.belmanexam.dal.implementations;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.bll.utils.Status;
import dk.easv.belmanexam.dal.repositories.OrderRepository;
import dk.easv.belmanexam.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public Collection<Order> getAll() {
        try(EntityManager em = JPAUtil.getEntityManager()){
            em.getTransaction().begin();
            List<Order> orders = em.createQuery("select o from Order o", Order.class).getResultList();
            if(!orders.isEmpty()){
                em.getTransaction().commit();
                return orders;
            }
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Order> getById(long id) {
        return Optional.empty();
    }

    @Override
    public Order add(Order order) {
        try (EntityManager em = JPAUtil.getEntityManager())
        {
            em.getTransaction().begin();
            Order o = em.merge(order);
            em.getTransaction().commit();
            return o;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public boolean delete(Order order) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Order managedOrder = em.merge(order);
            em.remove(managedOrder);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return false;
        }
        finally {
            em.close();
        }
    }

    @Override
    public Order update(Order updatedOrder) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Order mergedOrder = em.merge(updatedOrder);
            em.getTransaction().commit();
            return mergedOrder;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Order> getByOrderNumber(String orderNumber) {
        try(EntityManager em = JPAUtil.getEntityManager()) {
            TypedQuery<Order> query = em.createQuery("Select o from Order o where orderNumber = :order_number", Order.class);
            query.setParameter("order_number", orderNumber);
            if(query.getResultStream().findFirst().isEmpty()){
                throw new RuntimeException("No role with given id.");
            }
            return query.getResultStream().findFirst();
        }
    }
}
