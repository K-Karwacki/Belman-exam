package dk.easv.belmanexam.repositories.utils.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseMapper<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
