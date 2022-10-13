package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.testfx.matcher.control.LabeledMatchers;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;

public class DestinationControllerTest extends ApplicationTest {

    private DestinationController destinationController;

    private Parent root;

    private DestinationList destinationList;

    private TraveluHandler traveluHandler = new TraveluHandler();

    private List<String> activities;

    private DatePicker arrivalDatePicker;
    private DatePicker departureDatePicker;
    private Button setArrivalDate;
    private Button setDepartureDate;
    private Label arrivalDateLabel;
    private Label departureDateLabel;

    private ListView<String> activitiesListView;
    private TextField newActivityTextField;
    private Button addActivity;

    private TextField commentTextField;
    private Button updateComment;

    @Override
    public void start(Stage stage) throws IOException {

        destinationList = new DestinationList();
        destinationList.addDestination(new Destination("Spain", null, null, null, null));

        traveluHandler.writeJSON(destinationList, "testDestinationList.json");

        traveluHandler.writeJSON("Spain", "testCurrentDestinationName.json");

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destination.fxml"));
        root = fxmlLoader.load();
        destinationController = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();

        destinationController.initializeFromTestFiles();

        activities = destinationController.getDestinationActivities();

        arrivalDatePicker = lookup("#arrivalDatePicker").query();
        departureDatePicker = lookup("#departureDatePicker").query();
        setArrivalDate = lookup("#arrivalDateButton").query();
        setDepartureDate = lookup("#departureDateButton").query();

        activitiesListView = lookup("#activitiesListView").query();
        newActivityTextField = lookup("#newActivityTextField").query();
        addActivity = lookup("#addActivityButton").query();

        commentTextField = lookup("#commentTextField").query();
        updateComment = lookup("#updateButton").query();

        arrivalDateLabel = lookup("#arrivalDateLabel").query();
        departureDateLabel = lookup("#departureDateLabel").query();

    }

    @Test
    public void testDatePicker() {

        clickOn(arrivalDatePicker).write("05/02/2021");
        assertNotEquals("05/02/2021", arrivalDateLabel.getText());
        clickOn(setArrivalDate);
        assertNotEquals("06/02/2021", arrivalDateLabel.getText());
        assertEquals("05/02/2021", arrivalDateLabel.getText());

        clickOn(departureDatePicker).write("08/02/2021");
        assertNotEquals("08/02/2021", departureDateLabel.getText());
        clickOn(setDepartureDate);
        assertNotEquals("09/02/2021", departureDateLabel.getText());
        assertEquals("08/02/2021", departureDateLabel.getText());

        assertNotNull(destinationController.getDestinationDateInterval());

    }

    @Test
    public void testAddActivity() {

        clickOn(newActivityTextField).write("Take flamenco lessons");
        clickOn(addActivity);

        assertNotEquals(activities, activitiesListView.getItems());

        activities.add("Take flamenco lessons");
        activitiesListView = lookup("#activitiesListView").query();

        assertEquals(activities, activitiesListView.getItems());
    }

    @Test
    public void testWriteComment() {

        clickOn(commentTextField).write(
                "I traveled to Spain with my family and we visited restaurants every day");

        assertNotEquals(commentTextField.getText(), destinationController.getDestinationComment());
        clickOn(updateComment);

        assertEquals(commentTextField.getText(), destinationController.getDestinationComment());
    }

}