package dk.easv.belmanexam.repositories.implementations;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.repositories.interfaces.PhotoDocumentationRepository;
import dk.easv.belmanexam.repositories.utils.DBConnection;
import dk.easv.belmanexam.repositories.utils.QueryBuilder;
import dk.easv.belmanexam.repositories.utils.mappers.PhotoDocumentationMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class PhotoDocumentationRepositoryImpl implements PhotoDocumentationRepository {

    @Override public Collection<PhotoDocumentation> getAll()
    {
        QueryBuilder<PhotoDocumentation> queryBuilder = new QueryBuilder<>(PhotoDocumentation.class, "PhotoDocumentation")
                .withRowMapper(new PhotoDocumentationMapper());

        try (DBConnection dbConnection = new DBConnection()) {
            return queryBuilder.executeSelect(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override public Optional<PhotoDocumentation> getById(long id)
    {
        return Optional.empty();
    }

    @Override public PhotoDocumentation add(PhotoDocumentation entity)
    {
        return null;
    }

    @Override public boolean delete(PhotoDocumentation entity)
    {
        return false;
    }

    @Override public PhotoDocumentation update(PhotoDocumentation newEntity)
    {
        return null;
    }
}
