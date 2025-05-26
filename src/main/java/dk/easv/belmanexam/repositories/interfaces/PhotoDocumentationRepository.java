package dk.easv.belmanexam.repositories.interfaces;

import dk.easv.belmanexam.model.Log;
import dk.easv.belmanexam.model.Photo;
import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.model.User;

import java.util.Collection;
import java.util.Optional;


public interface PhotoDocumentationRepository extends BaseRepository<PhotoDocumentation>{
    Log addLog(User user, PhotoDocumentation newEntity);
    Collection<Log> getAllLogs();
    void addPhoto(byte[] data, long documentation_id, String side, String info);
    Optional<PhotoDocumentation> getByOrderNumber(String orderNumber);
    Collection<Photo> getAllImagesByDocumentationId(long id);
}
