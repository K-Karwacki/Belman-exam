package dk.easv.belmanexam.dal.implementations;

import dk.easv.belmanexam.be.Order;
import dk.easv.belmanexam.be.PhotoDocumentation;
import dk.easv.belmanexam.dal.repositories.PhotoDocumentationRepository;
import dk.easv.belmanexam.utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PhotoDocumentationRepositoryImpl implements PhotoDocumentationRepository {
    @Override
    public Collection<PhotoDocumentation> getAll() {
        try(EntityManager em = JPAUtil.getEntityManager()){
            em.getTransaction().begin();
            List<PhotoDocumentation> photoDocumentation = em.createQuery("select d from PhotoDocumentation d", PhotoDocumentation.class).getResultList();
            if(!photoDocumentation.isEmpty()){
                em.getTransaction().commit();
                return photoDocumentation;
            }
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<PhotoDocumentation> getById(long id) {
        return Optional.empty();
    }

    @Override
    public PhotoDocumentation add(PhotoDocumentation entity) {
        return null;
    }

    @Override
    public boolean delete(PhotoDocumentation entity) {
        return false;
    }

    @Override
    public PhotoDocumentation update(PhotoDocumentation newEntity) {
        return null;
    }
}
