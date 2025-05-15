package dk.easv.belmanexam.ui.models;

import dk.easv.belmanexam.model.PhotoDocumentation;
import dk.easv.belmanexam.model.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class UserListModel {
    private ObservableSet<UserModel> users = FXCollections.observableSet();

    public void setUsers(Set<UserModel> users) {
        this.users.addAll(users);
    }

    public void addUserModel(UserModel userModel){
        users.add(userModel);
    }

    public ObservableSet<UserModel> getUsersObservable()
    {
        return users;
    }

    public void deleteUserModel(UserModel userModel) {
        users.removeIf(u -> u.getID() == userModel.getID());
    }


    public void updateUser(UserModel userModel) {
        Iterator<UserModel> iterator = users.iterator();
        while (iterator.hasNext()) {
            UserModel u = iterator.next();
            if (u.getID() == userModel.getID()) {
                iterator.remove();
                users.add(userModel);
                break;
            }
        }
    }
}

