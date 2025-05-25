package dk.easv.belmanexam.repositories.interfaces;

import dk.easv.belmanexam.model.Log;
import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.model.User;

import java.util.Collection;


public interface PhotoDocumentationRepository extends BaseRepository<PhotoDocumentation>{
    Log addLog(User user, PhotoDocumentation newEntity);
    Collection<Log> getAllLogs();
}
