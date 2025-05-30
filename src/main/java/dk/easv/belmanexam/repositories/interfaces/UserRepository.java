package dk.easv.belmanexam.repositories.interfaces;

import dk.easv.belmanexam.entities.User;

public interface UserRepository extends BaseRepository<User>{
  User findByEmail(String email);
}
