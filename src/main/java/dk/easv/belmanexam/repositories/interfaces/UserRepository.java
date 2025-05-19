package dk.easv.belmanexam.repositories.interfaces;

import dk.easv.belmanexam.model.User;

public interface UserRepository extends BaseRepository<User>{
  User findByEmail(String email);
}
