package dk.easv.belmanexam;

import dk.easv.belmanexam.bll.factories.RepositoryService;
import dk.easv.belmanexam.bll.factories.RepositoryServiceFactory;
import dk.easv.belmanexam.bll.implementations.OrderManagementServiceImpl;
import dk.easv.belmanexam.bll.implementations.PhotoDocumentationServiceImpl;
import dk.easv.belmanexam.bll.interfaces.OrderManagementService;
import dk.easv.belmanexam.bll.interfaces.PhotoDocumentationManagementService;
import dk.easv.belmanexam.dal.GoogleDriveManager;
import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.StageManager;
import dk.easv.belmanexam.ui.ViewManager;
import dk.easv.belmanexam.ui.controllers.dashboards.operator.DocumentationCreationDashboardController;
import dk.easv.belmanexam.ui.controllers.dashboards.operator.OrdersDashboardController;
import dk.easv.belmanexam.ui.controllers.dashboards.qa.ApproveDocumentationDashboardController;
import dk.easv.belmanexam.ui.controllers.dashboards.qa.DocumentationDashboardController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

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

  @Override public void start(Stage primaryStage) {
    Locale.setDefault(Locale.ENGLISH);
    setControllersDependencies();
    stageManager.setCurrentStage(primaryStage);
    viewManager.setStageManager(stageManager);
    viewManager.showStage(FXMLPath.LOGIN_VIEW, "Login", false);
  }

  private void setControllersDependencies(){
    // Getting the controllers
    OrdersDashboardController ordersDashboardController = (OrdersDashboardController) fxmlManager.getFXML(FXMLPath.ORDERS_DASHBOARD).getValue();
    DocumentationDashboardController documentationDashboardController = (DocumentationDashboardController) fxmlManager.getFXML(FXMLPath.DOCUMENTATION_DASHBOARD).getValue();
    DocumentationCreationDashboardController documentationCreationDashboardController = (DocumentationCreationDashboardController) fxmlManager.getFXML(FXMLPath.DOCUMENTATION_CREATION_DASHBOARD).getValue();
    ApproveDocumentationDashboardController approveDocumentationDashboardController = (ApproveDocumentationDashboardController) fxmlManager.getFXML(FXMLPath.APPROVE_DOCUMENTATION_DASHBOARD).getValue();
    // Setting dependencies
    ordersDashboardController.setServices(orderManagementService, googleDriveManager);
    documentationDashboardController.setServices(photoDocumentationManagementService, googleDriveManager);
    documentationCreationDashboardController.setServices(photoDocumentationManagementService);
    approveDocumentationDashboardController.setServices(photoDocumentationManagementService);
  }

  public static void main(String[] args)
  {
    launch();
  }
}