package dk.easv.belmanexam.dal.repositories;

import dk.easv.belmanexam.be.User;

public interface UserRepository extends BaseRepository<User> {
    User getByEmail(String email);
}
