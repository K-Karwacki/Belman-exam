package dk.easv.belmanexam.services.implementations;

import dk.easv.belmanexam.entities.User;
import dk.easv.belmanexam.ui.models.UserListModel;
import dk.easv.belmanexam.ui.models.UserModel;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.services.factories.RepositoryService;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.utils.PasswordHasher;

import java.util.*;

public class UserManagementServiceImpl implements UserManagementService {

        private final RepositoryService repositoryService;
        private final UserRepository userRepository;

        private final UserListModel userListModel;

    public UserManagementServiceImpl(){
        repositoryService = null;
        userRepository = null;
        userListModel = null;
    }

    public UserManagementServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        userRepository = this.repositoryService.getRepository(UserRepository.class);

        userListModel = new UserListModel();

        initialize();
    }
    private void initialize(){
        List<User> fetchedUsers = userRepository.getAll().stream().toList();
        Set<UserModel> userModels = new HashSet<>();
        fetchedUsers.forEach(user ->
        {
            UserModel userModel = new UserModel(user);
            userModels.add(userModel);
        });
        userListModel.setUsers(userModels);
    }

        @Override
        public UserListModel getUserListModel() {
            return userListModel;
        }

    public UserModel createUser(String firstName, String lastName, String role, String email, String rawPassword) {
        User userEntity = new User();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setRole(role);
        userEntity.setEmail(email);
        userEntity.setPasswordHash(PasswordHasher.hashPassword(rawPassword));

        User savedUser = userRepository.add(userEntity);

        UserModel newUserModel = new UserModel(savedUser);
        userListModel.addUserModel(newUserModel);
        return newUserModel;
    }


    @Override public boolean updateUser(UserModel userModel)
        {
            Optional<User> optionalUser = userRepository.getById(userModel.getID());
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                if(userModel.getFirstName() != null){
                    user.setFirstName(userModel.getFirstName());
                }
                if(userModel.getLastName() != null){
                    user.setLastName(userModel.getLastName());
                }
                if(userModel.getRole() != null){
                    user.setRole(userModel.getRole());
                }
                if(userModel.getEmail() != null){
                    user.setEmail(userModel.getEmail());
                }
                userListModel.updateUser(userModel);
                return userRepository.update(user) != null;
            }
            return true;
        }

    @Override public boolean deleteUser(UserModel userModel)
    {
        Optional<User> user = userRepository.getById(userModel.getID());
        if(user.isPresent()){
            userListModel.deleteUserModel(userModel);
            userRepository.delete(user.get());
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
        public Optional<User> getUserById(long id) {
            return userRepository.getById(id);
        }
}

