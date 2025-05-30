package dk.easv.belmanexam.repositories.utils.mappers;

import dk.easv.belmanexam.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements BaseMapper<User>{
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("ID"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setRole(resultSet.getString("role"));
        user.setPasswordHash(resultSet.getString("hashed_password"));
        return user;
    }
}
