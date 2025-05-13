package dk.easv.belmanexam.repositories.utils.mappers;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.services.utils.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PhotoDocumentationMapper implements BaseMapper<PhotoDocumentation> {
    @Override
    public PhotoDocumentation mapRow(ResultSet resultSet) throws SQLException {
        PhotoDocumentation photoDocumentation = new PhotoDocumentation();
        photoDocumentation.setId(resultSet.getInt("id"));
        photoDocumentation.setOrderNumber(resultSet.getString("order_number"));
        photoDocumentation.setStatus(Status.fromString(resultSet.getString("status")));
        Timestamp timestamp = resultSet.getTimestamp("date");
        if (timestamp != null) {
            photoDocumentation.setDateTime(timestamp.toLocalDateTime());
        }
        return photoDocumentation;
    }
}
