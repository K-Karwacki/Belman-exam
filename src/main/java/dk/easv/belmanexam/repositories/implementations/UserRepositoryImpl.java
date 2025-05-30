package dk.easv.belmanexam.repositories.implementations;

import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.entities.User;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.repositories.utils.DBConnection;
import dk.easv.belmanexam.repositories.utils.QueryBuilder;
import dk.easv.belmanexam.repositories.utils.mappers.UserMapper;
import dk.easv.belmanexam.services.utils.Status;

import java.sql.*;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {
    private final UserMapper userMapper = new UserMapper();

    @Override
    public Collection<User> getAll() {
        QueryBuilder<User> queryBuilder = new QueryBuilder<>(User.class, "[User]")
                .withRowMapper(userMapper);
        try (DBConnection dbConnection = new DBConnection()) {
            Collection<User> users = queryBuilder.executeSelect(dbConnection.getConnection());
            if(users.iterator().hasNext()) {
                return users;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Optional<User> getById(long id) {
        QueryBuilder<User> queryBuilder = new QueryBuilder<>(User.class, "[User]")
                .withRowMapper(userMapper)
                .where("ID", id);
        try (DBConnection dbConnection = new DBConnection()) {
            Collection<User> users = queryBuilder.executeSelect(dbConnection.getConnection());
            if(users.iterator().hasNext()) {
                return Optional.of(users.iterator().next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public User add(User entity) {
        QueryBuilder<User> queryBuilder = new QueryBuilder<>(User.class, "[User]")
                .set("email", entity.getEmail())
                .set("first_name", entity.getFirstName())
                .set("last_name", entity.getLastName())
                .set("role", entity.getRole())
                .set("hashed_password", entity.getPasswordHash());

        try (DBConnection dbConnection = new DBConnection()) {
            queryBuilder.executeInsert(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }


    @Override
    public boolean delete(User entity) {
        System.out.println("DELETING USER: " + entity);
        QueryBuilder<User> queryBuilder = new QueryBuilder<>(User.class, "[User]")
                .where("ID", entity.getId());
        try (DBConnection dbConnection = new DBConnection()) {
            queryBuilder.executeDelete(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    @Override
    public User update(User entity) {
        QueryBuilder<User> queryBuilder = new QueryBuilder<>(User.class, "[User]")
                .where("ID", entity.getId())
                .set("email", entity.getEmail())
                .set("first_name", entity.getFirstName())
                .set("last_name", entity.getLastName())
                .set("role", entity.getRole())
                .set("hashed_password", entity.getPasswordHash());
        try (DBConnection dbConnection = new DBConnection()) {
            queryBuilder.executeUpdate(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override public User findByEmail(String email)
    {
        QueryBuilder<User> queryBuilder = new QueryBuilder<>(User.class, "[User]")
                .withRowMapper(userMapper)
                .where("email", email);
        try (DBConnection dbConnection = new DBConnection()) {
            Collection<User> users = queryBuilder.executeSelect(dbConnection.getConnection());
            if(users.iterator().hasNext()) {
                return users.iterator().next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
