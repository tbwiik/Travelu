package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.testfx.matcher.control.LabeledMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;

import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.fxutil.TraveluHandler;

/**
 * JavaFX tests for DestinationController
 */
@TestInstance(Lifecycle.PER_CLASS) // For import of external headless function
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
    private Label feedBackLabel;

    private ListView<String> activitiesListView;
    private TextField newActivityTextField;
    private Button addActivity;

    private TextField commentTextField;
    private Button updateComment;

    /**
     * Enables headless-testing
     */
    @BeforeAll
    public void setupHeadless() {
        TestHelperMethods.supportHeadless();
    }

    /**
     * Tests if app works as intended
     */
    @Override
    public void start(Stage stage) throws IOException {

        destinationList = new DestinationList();
        destinationList.addDestination(new Destination("Spain", new DateInterval(), 0, null, null));

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

        feedBackLabel = lookup("#dateUpdatedFeedbackLabel").query();

    }

    /**
     * Tests if you can pick different dates for arrival and departure
     */
    @Test
    public void testDatePicker() {

        // dates in format dd/MM/yyyy
        String arrivalDate = "05/02/2021";
        String departureDate = "19/10/2021";
        String invalidDate = "11/13/2021";

        String arrivalDateAfterDepartureDate = "21/10/2021";
        String departureDateBeforeArrivalDate = "10/01/2021";

        clickOn(arrivalDatePicker).write(arrivalDate);
        assertNotEquals(arrivalDate, arrivalDateLabel.getText());
        clickOn(setArrivalDate);

        assertEquals(arrivalDate, arrivalDateLabel.getText());

        clickOn(departureDatePicker).write(departureDate);
        assertNotEquals(departureDate, departureDateLabel.getText());
        clickOn(setDepartureDate);

        assertEquals(departureDate, departureDateLabel.getText());

        clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                .write(invalidDate);
        clickOn(setArrivalDate);
        //TODO: These tests fail. These errors are handled and not thrown
        //assertThrows(java.lang.RuntimeException.class, () -> clickOn(setArrivalDate));

        assertNotEquals(invalidDate, arrivalDateLabel.getText());
        assertEquals("Invalid arrival date.", feedBackLabel.getText());

        clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                .write(invalidDate);
        clickOn(setDepartureDate);
        //assertThrows(java.lang.RuntimeException.class, () -> clickOn(setDepartureDate));

        assertNotEquals(invalidDate, arrivalDateLabel.getText());
        assertEquals("Invalid departure date.", feedBackLabel.getText());

        clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                .write(arrivalDateAfterDepartureDate);
        clickOn(setArrivalDate);
        assertEquals(arrivalDate, arrivalDateLabel.getText());
        assertEquals("Arrival date must be before departure date.", feedBackLabel.getText());

        clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                .write(arrivalDate);
        clickOn(setArrivalDate);
        assertEquals(arrivalDate, arrivalDateLabel.getText());
        assertEquals("", feedBackLabel.getText());

        clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                .write(departureDateBeforeArrivalDate);
        clickOn(setDepartureDate);
        assertEquals(departureDate, departureDateLabel.getText());
        assertEquals("Arrival date must be before departure date.", feedBackLabel.getText());

        assertNotNull(destinationController.getDestinationDateInterval());

    }

    // /**
    // * Tests if you can add activity to current destination
    // */
    // @Test
    // public void testAddActivity() {

    // clickOn(newActivityTextField).write("Take flamenco lessons");
    // clickOn(addActivity);

    // assertNotEquals(activities, activitiesListView.getItems());

    // activities.add("Take flamenco lessons");
    // activitiesListView = lookup("#activitiesListView").query();

    // assertEquals(activities, activitiesListView.getItems());
    // }

    // /**
    // * Tests if you can write comment to current destination
    // */
    // @Test
    // public void testWriteComment() {

    // clickOn(commentTextField).write(
    // "I traveled to Spain with my family and we visited restaurants every day");

    // assertNotEquals(commentTextField.getText(),
    // destinationController.getDestinationComment());
    // clickOn(updateComment);

    // assertEquals(commentTextField.getText(),
    // destinationController.getDestinationComment());
    // }

}