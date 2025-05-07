package dk.easv.belmanexam.ui.controllers.dashboards.qa;

import dk.easv.belmanexam.ui.FXMLManager;
import dk.easv.belmanexam.ui.FXMLPath;
import dk.easv.belmanexam.ui.controllers.components.DocumentationListComponent;
import dk.easv.belmanexam.ui.controllers.components.OrderListComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class DocumentationDashboardController implements Initializable {
    @FXML
    private FlowPane flowPaneOrderList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // @ToDo -> Remove mock data later
        Pair<Parent, DocumentationListComponent> p1 = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
        p1.getValue().setOrderNumber("2024/12/1113");
        Pair<Parent, DocumentationListComponent> p2 = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
        p2.getValue().setOrderNumber("2025/09/1941");
        Pair<Parent, DocumentationListComponent> p3 = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
        p3.getValue().setOrderNumber("2025/21/1231");
        Pair<Parent, DocumentationListComponent> p4 = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
        p4.getValue().setOrderNumber("2025/11/1331");
        Pair<Parent, DocumentationListComponent> p5 = FXMLManager.INSTANCE.loadFXML(FXMLPath.DOCUMENTATION_LIST_COMPONENT);
        p5.getValue().setOrderNumber("2022/10/101");
        flowPaneOrderList.getChildren().addAll(p1.getKey(), p2.getKey(), p3.getKey(), p4.getKey(), p5.getKey());
    }
}
