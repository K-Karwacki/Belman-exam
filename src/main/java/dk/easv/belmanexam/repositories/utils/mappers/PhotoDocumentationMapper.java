package dk.easv.belmanexam.repositories.utils.mappers;

import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.services.utils.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PhotoDocumentationMapper implements BaseMapper<PhotoDocumentation> {
    private final UserRepository userRepository;

    public PhotoDocumentationMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PhotoDocumentation mapRow(ResultSet resultSet) throws SQLException {
        PhotoDocumentation photoDocumentation = new PhotoDocumentation();
        String operatorId = resultSet.getString("operatorID");
//        int userId = resultSet.getInt("user_id");
//        User user = userRepository.getById(userId)
//                .orElseThrow(() -> new SQLException("User not found for ID: " + userId));
//        UserModel userModel = new UserModel(user);
//        photoDocumentation.setUser(userModel);
        photoDocumentation.setOperatorId(operatorId);
        photoDocumentation.setId(resultSet.getLong("id"));
        photoDocumentation.setOrderNumber(resultSet.getString("orderNumber"));
        photoDocumentation.setStatus(Status.fromString(resultSet.getString("status")));
        Timestamp timestamp = resultSet.getTimestamp("date");
        if (timestamp != null) {
            photoDocumentation.setDateTime(timestamp.toLocalDateTime());
        }
        return photoDocumentation;
    }
}
