package travelu.fxui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

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
import travelu.localpersistence.TraveluHandler;

/**
 * JavaFX tests for DestinationListController
 */
@TestInstance(Lifecycle.PER_CLASS) // For import of external headless function
public class DestinationListControllerTest extends ApplicationTest {

        private Parent root;

        private DestinationList destinationList;

        private TextArea destinationText;
        private Button addButton;
        private Button removeButton;
        private Button nameButton;
        private Button ratingButton;
        private Label feedbackLabel;
        private ListView<String> listView;

        /**
         * Enables headless testing
         */
        @BeforeAll
        public void setupHeadless() {
                TestHelperMethods.supportHeadless();
        }

        @BeforeAll
        public void startWireMockServer() {
                WireMockConfiguration wireMockConfiguration = WireMockConfiguration.wireMockConfig().port(8080);
                WireMockServer wireMockServer = new WireMockServer(wireMockConfiguration.portNumber());
                wireMockServer.start();

                WireMock.configureFor("localhost", wireMockConfiguration.portNumber());

                // getting destinationList
                stubFor(get(urlEqualTo("/api/v1/entries/destinationlist"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")
                                                .withBody("{\"destinations\": [{\"name\": \"Costa Rica\",\"dateInterval\": {\"arrivalDate\": null,\"departureDate\": null},\"rating\": 0,\"activities\": [],\"comment\": null},{\"name\": \"Finland\",\"dateInterval\": {\"arrivalDate\": null,\"departureDate\": null},\"rating\": 4,\"activities\": [],\"comment\": null},{\"name\": \"Norway\",\"dateInterval\": {\"arrivalDate\": null,\"departureDate\": null},\"rating\": 2,\"activities\": [],\"comment\": null}]}")));

                // adding destination
                stubFor(post(urlEqualTo("/api/v1/entries/add"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

                // removing destination
                stubFor(post(urlEqualTo("/api/v1/entries/remove"))
                                .willReturn(aResponse()
                                                .withStatus(200)
                                                .withHeader("Content-Type", "application/json")));

        }

        @BeforeEach
        private void start() {
                destinationText = lookup("#destinationText").query();
                addButton = lookup("#addButton").query();
                removeButton = lookup("#removeButton").query();
                nameButton = lookup("#nameButton").query();
                ratingButton = lookup("#ratingButton").query();
                feedbackLabel = lookup("#feedbackLabel").query();
                listView = lookup("#listView").query();
        }

        /**
         * Tests if DestinationList works as intended
         */
        @Override
        public void start(Stage stage) throws IOException {

                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destinationList.fxml"));
                root = fxmlLoader.load();
                stage.setScene(new Scene(root));
                stage.show();

        }

        /**
         * Tests if the add button works as intended
         */
        @Test
        public void testAddDestination() {
                // valid input
                clickOn(destinationText).write("Helsinki");
                clickOn(addButton);

                assertEquals("", destinationText.getText());
                assertEquals("", feedbackLabel.getText());

                // empty input
                clickOn(addButton);

                assertEquals("", destinationText.getText());
                assertEquals("", feedbackLabel.getText());

                // invalid input
                String invalidInput = "51*@4´,a#";
                destinationText.setText(invalidInput);

                clickOn(addButton);

                assertEquals(invalidInput, destinationText.getText());
                assertEquals("Destination name must contain only letters, spaces and dashes", feedbackLabel.getText());

        }

        /**
         * Tests if adding duplicate destinations works as intended
         */
        @Test
        public void testAddDuplicates() {

                // duplicate input
                clickOn(destinationText).write("Norway");
                clickOn(addButton);

                assertEquals("Norway", destinationText.getText());
                assertEquals("You have already registered this destination", feedbackLabel.getText());

                // case insensitive duplicate input
                clickOn(destinationText).eraseText(destinationText.getText().length()).write("fInLaNd");
                clickOn(addButton);

                assertEquals("fInLaNd", destinationText.getText());
                assertEquals("You have already registered this destination", feedbackLabel.getText());

                // duplicate input with spaces
                clickOn(destinationText).eraseText(destinationText.getText().length()).write("  Costa Rica  ");
                clickOn(addButton);

                assertEquals("  Costa Rica  ", destinationText.getText());
                assertEquals("You have already registered this destination", feedbackLabel.getText());

        }

        /**
         * Tests if the remove button works as intended
         */
        @Test
        public void testRemoveDestination() {
                // clicking on remove before selecting destination
                clickOn(removeButton);

                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

                // selecting destination and clicking on remove
                clickOn("Norway★★");
                clickOn(removeButton);

                assertEquals("", feedbackLabel.getText());

                // clicking remove again
                clickOn(removeButton);

                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

                // clicking on list-view and clicking remove
                clickOn(listView);
                clickOn(removeButton);

                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

        }

}