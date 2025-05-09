package dk.easv.belmanexam.repositories.implementations;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.repositories.interfaces.PhotoDocumentationRepository;

import java.util.Collection;
import java.util.Optional;

public class PhotoDocumentationRepositoryImpl implements PhotoDocumentationRepository {

    @Override public Collection<PhotoDocumentation> getAll()
    {
        return null;
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
