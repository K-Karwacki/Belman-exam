package dk.easv.belmanexam.repositories.utils.mappers;

import dk.easv.belmanexam.model.Log;
import dk.easv.belmanexam.model.User;
import dk.easv.belmanexam.model.UserModel;
import dk.easv.belmanexam.repositories.implementations.UserRepositoryImpl;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.services.utils.ActionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogMapper implements BaseMapper<Log> {
    private final UserRepository userRepository;

    public LogMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Log mapRow(ResultSet resultSet) throws SQLException {
        Log log = new Log();
        log.setId(resultSet.getInt("id"));
        log.setOrderNumber(resultSet.getString("order_number"));

        // Fetch User object and convert to UserModel
        int userId = resultSet.getInt("user_id");
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new SQLException("User not found for ID: " + userId));
        UserModel userModel = new UserModel(user);
        log.setUser(userModel);

        log.setActionType(ActionType.fromString(resultSet.getString("action_type")));
        Timestamp timestamp = resultSet.getTimestamp("date");
        if (timestamp != null) {
            log.setDateTime(timestamp.toLocalDateTime());
        }
        return log;
    }
}