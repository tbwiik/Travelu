package travelu.fxui;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

/**
 * JavaFX tests for DestinationController, isolated from server
 * <p>
 * Due to the way the controllers are implemented, some of these tests are
 * dependent on validation in core.
 */
@TestInstance(Lifecycle.PER_CLASS) // For import of external headless function
public class DestinationControllerTest extends ApplicationTest {

        private Parent root;

        private DatePicker arrivalDatePicker;
        private DatePicker departureDatePicker;
        private Button setArrivalDate;
        private Button setDepartureDate;
        private Label departureDateLabel;
        private Label arrivalDateLabel;

        private Label dateUpdatedFeedbackLabel;
        private Label commentFeedbackLabel;
        private Label activityFeedbackLabel;

        private TextField newActivityTextField;
        private Button addActivity;
        private Button removeActivity;
        private ListView<String> activitiesListView;

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

                // Getting Finland destination
                stubFor(get(urlEqualTo("/api/v1/entries/Spain"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody(
                                                                "{\"name\": \"Spain\",\"dateInterval\": {\"arrivalDate\": null,\"departureDate\": null},\"rating\": 0,\"activities\": [\"Go to the beach\"],\"comment\": \"\"}")));

                // Getting current destination
                stubFor(get(urlEqualTo("/api/v1/entries/currentDestination"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody(
                                                                "Spain")));

                // Adding activity
                stubFor(post(urlEqualTo("/api/v1/entries/addActivity"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

                // Removing activity "Go to the beach"
                stubFor(delete(urlEqualTo("/api/v1/entries/removeActivity/Go%20to%20the%20beach"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

                // Removing activity
                stubFor(delete(urlEqualTo("/api/v1/entries/removeActivity"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

                // Setting rating
                stubFor(put(urlEqualTo("/api/v1/entries/setRating"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

                // setting arrival date
                stubFor(put(urlEqualTo("/api/v1/entries/setArrivalDate"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

                // Setting departure date
                stubFor(put(urlEqualTo("/api/v1/entries/setDepartureDate"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

                // Updating comment
                stubFor(put(urlEqualTo("/api/v1/entries/updateComment"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));
        }

        /**
         * Stop WireMock server
         */
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
                arrivalDateLabel = lookup("#arrivalDateLabel").query();
                departureDateLabel = lookup("#departureDateLabel").query();

                newActivityTextField = lookup("#newActivityTextField").query();
                addActivity = lookup("#addActivityButton").query();
                removeActivity = lookup("#removeActivityButton").query();
                activitiesListView = lookup("#activitiesListView").query();

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
         * Initializes GUI
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
         * <p>
         * This test is dependent on validation from core
         */
        @Test
        public void testSetDates() {

                // Dates in format dd/MM/yyyy
                String validArrivalDate = "05/02/2021";
                String validDepartureDate = "19/10/2021";
                String invalidDate = "11/13/2021";

                String arrivalDateAfterDepartureDate = "21/10/2021";
                String departureDateBeforeArrivalDate = "10/01/2021";

                // Ensure that there are no post requests to the server yet
                wireMockServer.verify(0, putRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));
                wireMockServer.verify(0, putRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

                // Input valid arrival date
                clickOn(arrivalDatePicker).write(validArrivalDate);
                clickOn(setArrivalDate);

                // Check if there is no feedback
                assertEquals("", dateUpdatedFeedbackLabel.getText());

                // Check if arrivalDateLabel is correctly updated
                assertEquals(validArrivalDate, arrivalDateLabel.getText());

                // Should post request to server
                wireMockServer.verify(1, putRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

                // Input valid departure date
                clickOn(departureDatePicker).write(validDepartureDate);
                clickOn(setDepartureDate);

                // Check if there is no feedback
                assertEquals("", dateUpdatedFeedbackLabel.getText());

                // Check if departureDateLabel is correctly updated
                assertEquals(validDepartureDate, departureDateLabel.getText());

                // Should post request to server
                wireMockServer.verify(1, putRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

                // Input invalid arrival date
                clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                                .write(invalidDate);
                clickOn(setArrivalDate);

                // Should not post request to server
                wireMockServer.verify(1, putRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

                assertEquals("Invalid arrival date.", dateUpdatedFeedbackLabel.getText());

                // Input invalid departure date
                clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                                .write(invalidDate);
                clickOn(setDepartureDate);

                // Should not post request to server
                wireMockServer.verify(1, putRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

                assertEquals("Invalid departure date.", dateUpdatedFeedbackLabel.getText());

                // Date labels should be unchanged
                assertEquals(validArrivalDate, arrivalDateLabel.getText());
                assertEquals(validDepartureDate, departureDateLabel.getText());

                // Input arrival date after departure date
                clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                                .write(arrivalDateAfterDepartureDate);
                clickOn(setArrivalDate);

                // Should not post request to server
                wireMockServer.verify(1, putRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

                assertEquals("Arrival date must be before departure date.", dateUpdatedFeedbackLabel.getText());

                // arrivalDateLabel should be unchanged
                assertEquals(validArrivalDate, arrivalDateLabel.getText());

                // Input valid arrival date, check that feedback label is cleared
                clickOn(arrivalDatePicker).eraseText(arrivalDatePicker.getEditor().getText().length())
                                .write(validArrivalDate);
                clickOn(setArrivalDate);

                // Should post request to server
                wireMockServer.verify(2, putRequestedFor(urlEqualTo("/api/v1/entries/setArrivalDate")));

                assertEquals("", dateUpdatedFeedbackLabel.getText());

                // Input departure date before arrival date
                clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                                .write(departureDateBeforeArrivalDate);
                clickOn(setDepartureDate);

                // Should not post request to server
                wireMockServer.verify(1, putRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

                assertEquals("Arrival date must be before departure date.", dateUpdatedFeedbackLabel.getText());

                // Departure date label should be unchanged
                assertEquals(validDepartureDate, departureDateLabel.getText());

                // Input valid departure date, check that feedback label is cleared
                clickOn(departureDatePicker).eraseText(departureDatePicker.getEditor().getText().length())
                                .write(validDepartureDate);
                clickOn(setDepartureDate);

                // Should post request to server
                wireMockServer.verify(2, putRequestedFor(urlEqualTo("/api/v1/entries/setDepartureDate")));

                assertEquals("", dateUpdatedFeedbackLabel.getText());

        }

        /**
         * Tests that listview correctly displays activities gotten from the mock server
         */
        @Test
        public void testActivitiesListViewUpdated() {
                assertEquals("[Go to the beach]", activitiesListView.getItems().toString());
        }

        /**
         * Tests adding activity to current destination
         */
        @Test
        public void testAddActivity() {

                // Valid input
                clickOn(newActivityTextField).write("Take flamenco lessons");
                clickOn(addActivity);

                // Should post request to server
                wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

                // activitiesListView should be updated
                assertEquals("[Go to the beach, Take flamenco lessons]", activitiesListView.getItems().toString());

                // activityFeedbackLabel should be empty
                // newActivityTextField should be reset
                assertEquals("", activityFeedbackLabel.getText());
                assertEquals("", newActivityTextField.getText());

                // Test empty input
                clickOn(newActivityTextField).write("");
                clickOn(addActivity);

                // Should not post request to server
                wireMockServer.verify(1, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

                // activityFeedbackLabel should be updated
                assertEquals("Add unique activity to update.", activityFeedbackLabel.getText());

                // activitiesListView should be unchanged
                assertEquals("[Go to the beach, Take flamenco lessons]", activitiesListView.getItems().toString());

                // Test if label is reset after valid input
                clickOn(newActivityTextField).eraseText(newActivityTextField.getText().length()).write("Dance salsa");
                clickOn(addActivity);

                // Should post request to server
                wireMockServer.verify(2, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

                // activityFeedbackLabel should be reset
                assertEquals("", activityFeedbackLabel.getText());
                assertEquals("", newActivityTextField.getText());

                // activitiesListView should be updated
                assertEquals("[Go to the beach, Take flamenco lessons, Dance salsa]",
                                activitiesListView.getItems().toString());

                // Test adding existing activity
                clickOn(newActivityTextField).write("Go to the beach");
                clickOn(addActivity);

                // Should not post request to server
                wireMockServer.verify(2, postRequestedFor(urlEqualTo("/api/v1/entries/addActivity")));

                // activityFeedbackLabel should be updated
                assertEquals("Add unique activity to update.", activityFeedbackLabel.getText());
                // newActivityTextField should be emptied
                assertEquals("", newActivityTextField.getText());

                // activitiesListView should be unchanged
                assertEquals("[Go to the beach, Take flamenco lessons, Dance salsa]",
                                activitiesListView.getItems().toString());

        }

        /**
         * Tests removing activity from current destination
         */
        @Test
        public void testRemoveActivity() {

                // Clicking remove activity with no activity selected should not post server
                // request
                clickOn(removeActivity);

                wireMockServer.verify(0, deleteRequestedFor(urlEqualTo("/api/v1/entries/removeActivity")));

                // Selecting and removing activity should post specific server request for
                // removing "Go to the beach"
                clickOn("Go to the beach");
                clickOn(removeActivity);

                wireMockServer.verify(1,
                                deleteRequestedFor(urlEqualTo("/api/v1/entries/removeActivity/Go%20to%20the%20beach")));

                // Clicking remove activity with no activity selected should not post server
                clickOn(removeActivity);

                wireMockServer.verify(0, deleteRequestedFor(urlEqualTo("/api/v1/entries/removeActivity")));

                // activitiesListView should now be empty
                assertEquals(true, activitiesListView.getItems().isEmpty());

        }

        /**
         * Tests writing comment to current destination
         */
        @Test
        public void testWriteComment() {

                // Valid input
                clickOn(commentTextField).write(
                                "I traveled to Spain with my family");
                clickOn(updateComment);

                // Should post request to server
                wireMockServer.verify(1, putRequestedFor(urlEqualTo("/api/v1/entries/updateComment")));

                // commentFeedBackLabel should be updated
                assertEquals("Comment updated!", commentFeedbackLabel.getText());

                clickOn(commentTextField).eraseText(commentTextField.getText().length());
                clickOn(updateComment);

                // Should post request to server
                wireMockServer.verify(2, putRequestedFor(urlEqualTo("/api/v1/entries/updateComment")));

        }

        /**
         * Tests if the correct number of stars are filled
         * <p>
         * Star coloring is handled entirely by the controller,
         * so this test is isolated from core
         */
        @Test
        public void testRating() {
                // List of star objects
                List<SVGPath> stars = new ArrayList<>(
                        List.of(star1, star2, star3, star4, star5)
                );

                // All stars should be white at beginning
                stars.forEach(star -> assertEquals("-fx-fill: #FFFFFF", star.getStyle()));
                // No server requests should have been made
                wireMockServer.verify(0, putRequestedFor(urlEqualTo("/api/v1/entries/setRating")));

                // Iterate through all five stars
                for (int i = 1; i <= 5; i++) {
                        // Click on current star
                        clickOn(stars.get(i-1));

                        // Assert that every star until current star is yellow
                        stars.subList(0, i).forEach(star -> assertEquals("-fx-fill: #FFD700", star.getStyle()));
                        // Assert that every star after current star is white
                        stars.subList(i, 5).forEach(star -> assertEquals("-fx-fill: #FFFFFF", star.getStyle()));
                        // Assert that there have been i server requests for setting rating
                        wireMockServer.verify(i, putRequestedFor(urlEqualTo("/api/v1/entries/setRating")));
                }

                // Click on first star again
                clickOn(star1);
                // First star should be yellow, rest should be white
                assertEquals("-fx-fill: #FFD700", star1.getStyle());
                stars.subList(1, 5).forEach(star -> assertEquals("-fx-fill: #FFFFFF", star.getStyle()));

                wireMockServer.verify(6, putRequestedFor(urlEqualTo("/api/v1/entries/setRating")));

        }

}