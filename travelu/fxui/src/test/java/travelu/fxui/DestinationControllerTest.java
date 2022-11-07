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
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;

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
     * Tests if app works as intended
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

    }

    /**
     * Tests if you can pick different dates for arrival and departure
     */
    @Test
    public void testDatePicker() {

        String startDate = "5/2/2021";
        String endDate = "8/2/2021";
        String errorDate = "10/10/2030";

        clickOn(arrivalDatePicker).write(startDate);
        assertNotEquals(startDate, arrivalDateLabel.getText());
        clickOn(setArrivalDate);
        assertNotEquals(errorDate, arrivalDateLabel.getText());
        // assertEquals(startDate, arrivalDateLabel.getText());

        clickOn(departureDatePicker).write(endDate);
        assertNotEquals(endDate, departureDateLabel.getText());
        clickOn(setDepartureDate);
        assertNotEquals(errorDate, departureDateLabel.getText());

        assertNotNull(destinationController.getDestinationDateInterval());

    }

    /**
     * Tests if you can add activity to current destination
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
     * Tests if you can remove activities from current destination
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
     * Tests if you can write comment to current destination
     */
    @Test
    public void testWriteComment() {

        clickOn(commentTextField).write(
                "I traveled to Spain with my family and we visited restaurants every day");

        assertNotEquals(commentTextField.getText(), destinationController.getDestinationComment());
        clickOn(updateComment);

        assertEquals(commentTextField.getText(), destinationController.getDestinationComment());
    }

    /**
     * Test if you the correct number of stars are filled
     * Test if destination rating is updated correctly
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