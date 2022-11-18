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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;

/**
 * JavaFX tests for DestinationController
 */
@TestInstance(Lifecycle.PER_CLASS) // For import of external headless function
public class DestinationControllerTest extends ApplicationTest {

    private Parent root;

    private DatePicker arrivalDatePicker;
    private DatePicker departureDatePicker;
    private Button setArrivalDate;
    private Button setDepartureDate;

    private Label dateUpdatedFeedbackLabel;
    private Label commentFeedbackLabel;
    private Label activityFeedbackLabel;

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

    private WireMockServer wireMockServer;

    /**
     * Enables headless-testing
     */
    @BeforeAll
    public void setupHeadless() {
        TestHelperMethods.supportHeadless();
    }

    /**
     * Sets up wiremock server
     */
    @BeforeAll
    public void startWireMockServer() {
        WireMockConfiguration wireMockConfiguration = WireMockConfiguration.wireMockConfig().port(8080);
        wireMockServer = new WireMockServer(wireMockConfiguration.portNumber());
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockConfiguration.portNumber());

        // getting finland destination
        stubFor(get(urlEqualTo("/api/v1/entries/Spain"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"name\": \"Spain\",\"dateInterval\": {\"arrivalDate\": null,\"departureDate\": null},\"rating\": 0,\"activities\": [\"Go to the beach\"],\"comment\": \"\"}")));

        // getting current destination
        stubFor(get(urlEqualTo("/api/v1/entries/currentDestination"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "Spain")));

        // adding activity
        stubFor(post(urlEqualTo("/api/v1/entries/addActivity"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        // removing activity
        stubFor(post(urlEqualTo("/api/v1/entries/removeActivity"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        // setting rating
        stubFor(post(urlEqualTo("/api/v1/entries/setRating"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        // setting arrival date
        stubFor(post(urlEqualTo("/api/v1/entries/setArrivalDate"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        // setting departure date
        stubFor(post(urlEqualTo("/api/v1/entries/setDepartureDate"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        // updating comment
        stubFor(post(urlEqualTo("/api/v1/entries/updateComment"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));
    }

    @AfterAll
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    /**
     * Initializes FXML elements
     */
    @BeforeEach
    public void start() throws IOException {

        arrivalDatePicker = lookup("#arrivalDatePicker").query();
        departureDatePicker = lookup("#departureDatePicker").query();
        setArrivalDate = lookup("#arrivalDateButton").query();
        setDepartureDate = lookup("#departureDateButton").query();

        newActivityTextField = lookup("#newActivityTextField").query();
        addActivity = lookup("#addActivityButton").query();
        removeActivity = lookup("#removeActivityButton").query();

        commentTextField = lookup("#commentTextField").query();
        updateComment = lookup("#updateButton").query();

        star1 = lookup("#star1").query();
        star2 = lookup("#star2").query();
        star3 = lookup("#star3").query();
        star4 = lookup("#star4").query();
        star5 = lookup("#star5").query();

        dateUpdatedFeedbackLabel = lookup("#dateUpdatedFeedbackLabel").query();
        commentFeedbackLabel = lookup("#commentFeedbackLabel").query();
        activityFeedbackLabel = lookup("#activityFeedbackLabel").query();

    }

    /**
     * Tests if DestinationList works as intended
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destination.fxml"));
        root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();

    }

    /**
     * Tests if the arrival date and departure date is set correctly
     */
    @Test
    public void testSetDates() {

        // dates in format dd/MM/yyyy
        String validArrivalDate = "05/02/2021";
        String validDepartureDate = "19/10/2021";
        String invalidDate = "11/13/2021";

        String arrivalDateAfterDepartureDate = "21/10/2021";
        String departureDateBeforeArrivalDate = "10/01/2021";

        // ensure that there are no post requests to the server yet
        wireMockServer.verify(0, postRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));
        wireMockServer.verify(0, postRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

        // input valid arrival date
        clickOn(arrivalDatePicker).write(validArrivalDate);
        clickOn(setArrivalDate);

        // check if there is no feedback
        assertEquals("", dateUpdatedFeedbackLabel.getText());

        // should post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

        // input valid departure date
        clickOn(departureDatePicker).write(validDepartureDate);
        clickOn(setDepartureDate);

        // check if there is no feedback
        assertEquals("", dateUpdatedFeedbackLabel.getText());

        // should post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

        // input invalid arrival date
        clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                .write(invalidDate);
        clickOn(setArrivalDate);

        // should not post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

        assertEquals("Invalid arrival date.", dateUpdatedFeedbackLabel.getText());

        // input invalid departure date
        clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                .write(invalidDate);
        clickOn(setDepartureDate);

        // should not post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

        assertEquals("Invalid departure date.", dateUpdatedFeedbackLabel.getText());

        // input arrival date after departure date
        clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                .write(arrivalDateAfterDepartureDate);
        clickOn(setArrivalDate);

        // should not post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

        assertEquals("Arrival date must be before departure date.", dateUpdatedFeedbackLabel.getText());

        // input valid arrival date, check that feedback label is cleared
        clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                .write(validArrivalDate);
        clickOn(setArrivalDate);

        // should post request to server
        wireMockServer.verify(2, postRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

        assertEquals("", dateUpdatedFeedbackLabel.getText());

        // input departure date before arrival date
        clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                .write(departureDateBeforeArrivalDate);
        clickOn(setDepartureDate);

        // should not post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

        assertEquals("Arrival date must be before departure date.", dateUpdatedFeedbackLabel.getText());

        // input valid departure date, check that feedback label is cleared
        clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                .write(validDepartureDate);
        clickOn(setDepartureDate);

        // should post request to server
        wireMockServer.verify(2, postRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

        assertEquals("", dateUpdatedFeedbackLabel.getText());

    }

    /**
     * Tests adding activity to current destination
     */
    @Test
    public void testAddActivity() {

        // valid input
        clickOn(newActivityTextField).write("Take flamenco lessons");
        clickOn(addActivity);

        // should post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

        // activityFeedbackLabel should be empty
        // newActivityTextField should be reset
        assertEquals("", activityFeedbackLabel.getText());
        assertEquals("", newActivityTextField.getText());

        // Test empty input
        clickOn(newActivityTextField).write("");
        clickOn(addActivity);

        // should not post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

        // activityFeedbackLabel should be updated
        assertEquals("Add unique activity to update.", activityFeedbackLabel.getText());

        // Test if label is reset after valid input
        clickOn(newActivityTextField).eraseText(newActivityTextField.getText().length()).write("Dance salsa");
        clickOn(addActivity);

        // should post request to server
        wireMockServer.verify(2, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

        // activityFeedbackLabel should be reset
        assertEquals("", activityFeedbackLabel.getText());
        assertEquals("", newActivityTextField.getText());

        // Test adding existing activity
        clickOn(newActivityTextField).write("Go to the beach");
        clickOn(addActivity);

        // should not post request to server
        wireMockServer.verify(2, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

        assertEquals("Add unique activity to update.", activityFeedbackLabel.getText());
        assertEquals("", newActivityTextField.getText());
    }

    /**
     * Tests removing activity from current destination
     */
    @Test
    public void testRemoveActivity() {

        // Clicking remove activity with no activity selected should not post server
        // request
        clickOn(removeActivity);

        wireMockServer.verify(0, postRequestedFor(urlEqualTo("/api/v1/entries/removeActivity")));

        // Selecting and removing activity should post server request
        clickOn("Go to the beach");
        clickOn(removeActivity);

        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/removeActivity")));

        // Clicking remove activity with no activity selected should not post server
        clickOn(removeActivity);

        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/removeActivity")));

    }

    /**
     * Tests writing comment to current destination
     */
    @Test
    public void testWriteComment() {

        // valid input
        clickOn(commentTextField).write(
                "I traveled to Spain with my family");
        clickOn(updateComment);

        // should post request to server
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/updateComment")));

        clickOn(commentTextField).eraseText(commentTextField.getText().length());
        clickOn(updateComment);

        // should post request to server
        wireMockServer.verify(2, postRequestedFor(urlEqualTo("/api/v1/entries/updateComment")));

    }
}