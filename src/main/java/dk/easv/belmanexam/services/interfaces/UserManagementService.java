package dk.easv.belmanexam.services.interfaces;

import dk.easv.belmanexam.entities.User;
import dk.easv.belmanexam.ui.models.UserListModel;
import dk.easv.belmanexam.ui.models.UserModel;

import java.util.Optional;

public interface UserManagementService {

    UserListModel getUserListModel();
    UserModel createUser(String firstName, String lastName, String role, String email, String rawPassword);
    boolean deleteUser(UserModel userModel);
    boolean updateUser(UserModel userModel);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(long id);
}