package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.testfx.matcher.control.LabeledMatchers;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

    private ListView<String> activitiesListView;
    private TextField newActivityTextField;
    private Button addActivity;

    private TextField commentTextField;
    private Button updateComment;

    @BeforeEach
    private void start() {

    }

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

        activities = destinationController.getDestinationObject().getActivities();

        arrivalDatePicker = lookup("#arrivalDatePicker").query();
        departureDatePicker = lookup("#departureDatePicker").query();
        setArrivalDate = lookup("#arrivalDateButton").query();
        setDepartureDate = lookup("#departureDateButton").query();

        activitiesListView = lookup("#activitiesListView").query();
        newActivityTextField = lookup("#newActivityTextField").query();
        addActivity = lookup("#addActivityButton").query();

        commentTextField = lookup("#commentTextField").query();
        updateComment = lookup("#updateButton").query();

    }

    @Test
    public void testDatePicker() {
        clickOn(arrivalDatePicker).write("05.02.2021");
        clickOn(setArrivalDate);

        clickOn(departureDatePicker).write("08.02.2021");
        clickOn(setDepartureDate);
    }

    @Test
    public void testAddActivity() {

        clickOn(newActivityTextField).write("New Activity");
        clickOn(addActivity);

        assertNotEquals(activities, activitiesListView.getItems());

        activities.add("New Activity");
        activitiesListView = lookup("#activitiesListView").query();

        assertEquals(activities, activitiesListView.getItems());
    }

    @Test
    public void testWriteComment() {

        clickOn(commentTextField).write(
                "I had a lot of fun in Spain");

        assertNotEquals(commentTextField.getText(), destinationController.getDestinationObject().getComment());
        clickOn(updateComment);

        assertEquals(commentTextField.getText(), destinationController.getDestinationObject().getComment());
    }

}