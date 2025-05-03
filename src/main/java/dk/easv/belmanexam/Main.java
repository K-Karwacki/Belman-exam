package dk.easv.belmanexam;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.StageManager;
import dk.easv.belmanexam.ui.ViewManager;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application
{
  private final ViewManager viewManager = ViewManager.INSTANCE;
  private final FXMLManager fxmlManager = FXMLManager.INSTANCE;
  private final StageManager stageManager = new StageManager();


  @Override public void start(Stage primaryStage) {
    Locale.setDefault(Locale.ENGLISH);
    stageManager.setCurrentStage(primaryStage);
    viewManager.setStageManager(stageManager);
    viewManager.showStage(FXMLPath.LOGIN_VIEW, "Login", false);
  }

  public static void main(String[] args)
  {
    launch();
  }
}