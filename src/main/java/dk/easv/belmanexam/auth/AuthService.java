package dk.easv.belmanexam.auth;

import dk.easv.belmanexam.model.User;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.services.factories.RepositoryService;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService
{
  private final UserRepository userRepository;

  public AuthService(RepositoryService repositoryService){
    this.userRepository = repositoryService.getRepository(UserRepository.class);
  }

  private User findUserWithEmail(String email){
    return userRepository.findByEmail(email);
  }

  public boolean authenticateWithPassword(String email, String password){
    User user = findUserWithEmail(email);
    if(user == null) return false;

    if (BCrypt.checkpw(password, user.getPasswordHash())) {
      UserSession.INSTANCE.setLoggedUser(user);
      return true;
    }
    return false;
  }
}
