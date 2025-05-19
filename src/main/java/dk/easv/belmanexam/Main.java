package dk.easv.belmanexam;

import com.gluonhq.attach.pictures.PicturesService;
import dk.easv.belmanexam.auth.AuthService;
import dk.easv.belmanexam.services.factories.RepositoryService;
import dk.easv.belmanexam.services.factories.RepositoryServiceFactory;
import dk.easv.belmanexam.services.implementations.OrderManagementServiceImpl;
import dk.easv.belmanexam.services.implementations.PhotoDocumentationServiceImpl;
import dk.easv.belmanexam.services.implementations.UserManagementServiceImpl;
import dk.easv.belmanexam.services.interfaces.OrderManagementService;
import dk.easv.belmanexam.services.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.repositories.utils.GoogleDriveManager;
import dk.easv.belmanexam.services.interfaces.UserManagementService;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.StageManager;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.admin.dashboards.UserCreatorController;
import dk.easv.belmanexam.ui.controllers.admin.dashboards.UserEditorController;
import dk.easv.belmanexam.ui.controllers.admin.dashboards.UsersDashboardController;
import dk.easv.belmanexam.ui.controllers.main.LoginViewController;
import dk.easv.belmanexam.ui.controllers.operator.dashboards.DocumentationCreationDashboardController;
import dk.easv.belmanexam.ui.controllers.operator.dashboards.OrdersDashboardController;
import dk.easv.belmanexam.ui.controllers.qa.dashboards.ApproveDocumentationDashboardController;
import dk.easv.belmanexam.ui.controllers.qa.dashboards.DocumentationDashboardController;
import dk.easv.belmanexam.ui.controllers.qa.dashboards.DocumentationPreviewController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Optional;



public class Main extends Application
{
  private final ViewManager viewManager = ViewManager.INSTANCE;
  private final FXMLManager fxmlManager = FXMLManager.INSTANCE;
  private final StageManager stageManager = new StageManager();

  protected final RepositoryServiceFactory repositoryServiceFactory = new RepositoryServiceFactory();
  protected final RepositoryService repositoryService = repositoryServiceFactory.getRepositoryService();

  protected final GoogleDriveManager googleDriveManager = new GoogleDriveManager();
  protected final OrderManagementService orderManagementService = new OrderManagementServiceImpl(repositoryService);
  protected final PhotoDocumentationManagementService photoDocumentationManagementService = new PhotoDocumentationServiceImpl(repositoryService);
  protected final UserManagementService userManagementService = new UserManagementServiceImpl(repositoryService);
  protected final AuthService authService = new AuthService(repositoryService);

  @Override public void start(Stage primaryStage) {

  Optional<PicturesService> picturesService = PicturesService.create();

  if(picturesService.isEmpty()){
    System.out.println("Service not available");
  }else{
    System.out.println("Service on");
  }



    Locale.setDefault(Locale.ENGLISH);
    setControllersDependencies();
    stageManager.setCurrentStage(primaryStage);
    viewManager.setStageManager(stageManager);
    viewManager.showStage(FXMLPath.LOGIN_VIEW, "Login", false);
  }

  private void setControllersDependencies(){
    // Getting the controllers
    LoginViewController loginViewController = (LoginViewController) fxmlManager.getFXML(FXMLPath.LOGIN_VIEW).getValue();
    OrdersDashboardController ordersDashboardController = (OrdersDashboardController) fxmlManager.getFXML(FXMLPath.ORDERS_DASHBOARD).getValue();
    DocumentationDashboardController documentationDashboardController = (DocumentationDashboardController) fxmlManager.getFXML(FXMLPath.DOCUMENTATION_DASHBOARD).getValue();
    DocumentationCreationDashboardController documentationCreationDashboardController = (DocumentationCreationDashboardController) fxmlManager.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD).getValue();
    ApproveDocumentationDashboardController approveDocumentationDashboardController = (ApproveDocumentationDashboardController) fxmlManager.getFXML(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD).getValue();
    UsersDashboardController usersDashboardController = (UsersDashboardController) fxmlManager.getFXML(FXMLPath.USERS_DASHBOARD).getValue();
    UserEditorController userEditorController = (UserEditorController) fxmlManager.getFXML(FXMLPath.USER_EDITOR_POPUP).getValue();
    UserCreatorController userCreatorController = (UserCreatorController) fxmlManager.getFXML(FXMLPath.USER_CREATOR_POPUP).getValue();
    DocumentationPreviewController documentationPreviewController = (DocumentationPreviewController) fxmlManager.getFXML(FXMLPath.DOCUMENTATION_PREVIEW).getValue();

    // Setting dependencies
    loginViewController.setServices(authService);
    ordersDashboardController.setServices(orderManagementService, googleDriveManager);
    documentationDashboardController.setServices(photoDocumentationManagementService, googleDriveManager);
    documentationCreationDashboardController.setServices(photoDocumentationManagementService);
    approveDocumentationDashboardController.setServices(photoDocumentationManagementService);
    documentationPreviewController.setServices(photoDocumentationManagementService);
    usersDashboardController.setServices(userManagementService);
    userEditorController.setServices(userManagementService);
    userCreatorController.setServices(userManagementService);
  }

  public static void main(String[] args)
  {
    launch();
  }
}