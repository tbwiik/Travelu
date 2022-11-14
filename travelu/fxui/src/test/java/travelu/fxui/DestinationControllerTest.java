package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;

import org.junit.jupiter.api.BeforeAll;

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
    private Button removeActivity;

    private TextField commentTextField;
    private Button updateComment;

    private SVGPath star1;
    private SVGPath star2;
    private SVGPath star3;
    private SVGPath star4;
    private SVGPath star5;

    /**
     * Enables headless-testing
     */
    @BeforeAll
    public void setupHeadless() {
        TestHelperMethods.supportHeadless();
    }

    /**
     * Initialize everything, declaring variables that are used later and setting up the files
     * TODO: clean up this method, and write better docs for it
     */
    @Override
    public void start(Stage stage) throws IOException {

        destinationList = new DestinationList();

        List<String> spainActivities = new ArrayList<>();
        spainActivities.add("Eat paella");

        destinationList.addDestination(new Destination("Spain", null, 0, spainActivities, null));

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
        removeActivity = lookup("#removeActivityButton").query();

        commentTextField = lookup("#commentTextField").query();
        updateComment = lookup("#updateButton").query();

        arrivalDateLabel = lookup("#arrivalDateLabel").query();
        departureDateLabel = lookup("#departureDateLabel").query();

        star1 = lookup("#star1").query();
        star2 = lookup("#star2").query();
        star3 = lookup("#star3").query();
        star4 = lookup("#star4").query();
        star5 = lookup("#star5").query();

        feedBackLabel = lookup("#dateUpdatedFeedbackLabel").query();

    }

    /**
     * Tests picking different dates for arrival and departure
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

        assertNotEquals(invalidDate, arrivalDateLabel.getText());
        assertEquals("Invalid arrival date.", feedBackLabel.getText());

        clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                .write(invalidDate);
        clickOn(setDepartureDate);

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

    /**
    * Tests adding activity to current destination
    */
    @Test
    public void testAddActivity() {

        clickOn(newActivityTextField).write("Take flamenco lessons");
        clickOn(addActivity);

        assertNotEquals(activities, activitiesListView.getItems());

        activities.add("Take flamenco lessons");

        assertEquals(activities, activitiesListView.getItems());
    }

    /**
     * Tests removing activity from destination
     */
    @Test
    public void testRemoveActivity() {
        List<String> spainActivities = new ArrayList<>();
        spainActivities.add("Eat paella");
        assertEquals(spainActivities, activitiesListView.getItems());

        clickOn("Eat paella");
        clickOn(removeActivity);

        assertNotEquals(spainActivities, activitiesListView.getItems());
        assertEquals(new ArrayList<>(), activitiesListView.getItems());
    }

    /**
     * Tests writing comment to current destination
     */
    @Test
    public void testWriteComment() {

    clickOn(commentTextField).write(
    "I traveled to Spain with my family and we visited restaurants every day");

    assertNotEquals(commentTextField.getText(),
    destinationController.getDestinationComment());
    clickOn(updateComment);

    assertEquals(commentTextField.getText(),
    destinationController.getDestinationComment());
    }

    /**
     * Tests if the correct number of stars are filled
     * Tests if destination rating is updated correctly
     */
    @Test
    public void testRating() {

        assertEquals("-fx-fill: #FFFFFF", star1.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star2.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star3.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star4.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star5.getStyle());

        assertEquals(0, destinationController.getDestinationRating());

        clickOn(star1);
        assertEquals("-fx-fill: #FFD700", star1.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star2.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star3.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star4.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star5.getStyle());
        assertEquals(1, destinationController.getDestinationRating());

        clickOn(star2);
        assertEquals("-fx-fill: #FFD700", star1.getStyle());
        assertEquals("-fx-fill: #FFD700", star2.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star3.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star4.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star5.getStyle());
        assertEquals(2, destinationController.getDestinationRating());

        clickOn(star3);
        assertEquals("-fx-fill: #FFD700", star1.getStyle());
        assertEquals("-fx-fill: #FFD700", star2.getStyle());
        assertEquals("-fx-fill: #FFD700", star3.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star4.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star5.getStyle());
        assertEquals(3, destinationController.getDestinationRating());

        clickOn(star4);
        assertEquals("-fx-fill: #FFD700", star1.getStyle());
        assertEquals("-fx-fill: #FFD700", star2.getStyle());
        assertEquals("-fx-fill: #FFD700", star3.getStyle());
        assertEquals("-fx-fill: #FFD700", star4.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star5.getStyle());
        assertEquals(4, destinationController.getDestinationRating());

        clickOn(star5);
        assertEquals("-fx-fill: #FFD700", star1.getStyle());
        assertEquals("-fx-fill: #FFD700", star2.getStyle());
        assertEquals("-fx-fill: #FFD700", star3.getStyle());
        assertEquals("-fx-fill: #FFD700", star4.getStyle());
        assertEquals("-fx-fill: #FFD700", star5.getStyle());
        assertEquals(5, destinationController.getDestinationRating());

        clickOn(star2);
        assertEquals("-fx-fill: #FFD700", star1.getStyle());
        assertEquals("-fx-fill: #FFD700", star2.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star3.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star4.getStyle());
        assertEquals("-fx-fill: #FFFFFF", star5.getStyle());
        assertEquals(2, destinationController.getDestinationRating());

    }


}