package dk.easv.belmanexam.repositories.utils.mappers;

import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.entities.User;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.services.utils.Status;
import dk.easv.belmanexam.ui.models.UserModel;

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
        String operatorEmail = resultSet.getString("operatorEmail");
        User user = userRepository.findByEmail(operatorEmail);
        UserModel userModel = new UserModel(user);
        photoDocumentation.setUser(userModel);
        photoDocumentation.setOperatorEmail(operatorEmail);
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
