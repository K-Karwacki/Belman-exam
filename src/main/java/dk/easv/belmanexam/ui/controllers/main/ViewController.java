package dk.easv.belmanexam.ui.controllers.main;

import dk.easv.belmanexam.ui.ViewManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    private final ViewManager viewManager = ViewManager.INSTANCE;

    @FXML private BorderPane root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewManager.setStageRoot(root);
    }
}
