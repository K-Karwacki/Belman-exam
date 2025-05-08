package dk.easv.belmanexam.ui.controllers.dashboards.admin;

import dk.easv.belmanexam.ui.ViewManager;
import javafx.application.Platform;

import static dk.easv.belmanexam.ui.FXMLPath.USER_CREATOR_POPUP;


public class UserCreatorController {

    public void submitButton() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    ViewManager.INSTANCE.hidePopup(USER_CREATOR_POPUP);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

