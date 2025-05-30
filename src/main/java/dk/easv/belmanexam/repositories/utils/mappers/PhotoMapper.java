package dk.easv.belmanexam.repositories.utils.mappers;

import dk.easv.belmanexam.entities.Photo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoMapper implements BaseMapper<Photo> {

    @Override
    public Photo mapRow(ResultSet resultSet) throws SQLException {
        Photo photo = new Photo();
        photo.setId(resultSet.getLong("id"));
        photo.setInfo(resultSet.getString("info"));
        photo.setImageData(resultSet.getBytes("data"));
        photo.setDocumentationId(resultSet.getLong("documentation_id"));
        photo.setSide(resultSet.getString("side"));
        return photo;
    }
}
