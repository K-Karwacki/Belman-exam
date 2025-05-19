package dk.easv.belmanexam.repositories.implementations;

import dk.easv.belmanexam.model.User;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.repositories.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM [user]";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<User> users = new LinkedList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }

            return Collections.unmodifiableList(users);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> getById(long id) {
        String sql = "SELECT * FROM [User] WHERE ID = ?";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                System.out.println(rs.getLong("id"));
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                return Optional.of(user);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user by ID: " + e.getMessage(), e);
        }
    }


    @Override
    public User add(User entity) {
        String sql = "INSERT INTO [user] (first_name, last_name, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getRole());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Inserting user failed, no ID obtained.");
                }
            }

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting user: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean delete(User entity) {
        String sql = "DELETE FROM [user] WHERE id = ?";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, entity.getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }


    @Override
    public User update(User entity) {
        String sql = "UPDATE [user] SET first_name = ?, last_name = ?, email = ?, role = ? WHERE id = ?";

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getRole());
            stmt.setLong(5, entity.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No rows affected when updating user");
            }
            return entity;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override public User findByEmail(String email)
    {
        return new User();
    }

}
