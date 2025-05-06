package dk.easv.belmanexam.dal.implementations;

import dk.easv.belmanexam.be.User;
import dk.easv.belmanexam.dal.repositories.UserRepository;
import dk.easv.belmanexam.utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Collection<User> getAll() {
        return List.of();
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.empty();
    }

    @Override
    public User add(User user) {
        try (EntityManager em = JPAUtil.getEntityManager())
        {
            em.getTransaction().begin();
            User u = em.merge(user);
            em.getTransaction().commit();
            return u;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public boolean delete(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User managedUser = em.merge(user);
            em.remove(managedUser);
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
    public User update(User updatedUser) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User mergedUser = em.merge(updatedUser);
            em.getTransaction().commit();
            return mergedUser;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }
}
