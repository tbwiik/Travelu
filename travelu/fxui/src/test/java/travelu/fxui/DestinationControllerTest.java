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


}