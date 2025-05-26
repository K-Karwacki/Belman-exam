package dk.easv.belmanexam.repositories.utils.mappers;

import dk.easv.belmanexam.model.Photo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoMapper implements BaseMapper<Photo> {

    @Override
    public Photo mapRow(ResultSet resultSet) throws SQLException {
        Photo photo = new Photo();
        photo.setId(resultSet.getLong("id"));
        photo.setinfo(resultSet.getString("info"));
        photo.setImageData(resultSet.getBytes("image_data"));
        photo.setDocumentationId(resultSet.getLong("documentation_id"));
        photo.setSide(resultSet.getString("side"));
        return photo;
    }
}
