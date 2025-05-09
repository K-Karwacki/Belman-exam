package dk.easv.belmanexam.repositories.implementations;

import dk.easv.belmanexam.model.User;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;

import java.util.Collection;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override public Collection<User> getAll()
    {
        return null;
    }

    @Override public Optional<User> getById(long id)
    {
        return Optional.empty();
    }

    @Override public User add(User entity)
    {
        return null;
    }

    @Override public boolean delete(User entity)
    {
        return false;
    }

    @Override public User update(User newEntity)
    {
        return null;
    }
}
