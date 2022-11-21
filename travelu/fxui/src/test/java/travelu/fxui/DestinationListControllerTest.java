package travelu.fxui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * JavaFX tests for DestinationListController, isolated from server
 * <p>
 * Due to the way the controllers are implemented, some of these tests are dependent on validation in core.
 */
@TestInstance(Lifecycle.PER_CLASS) // For import of external headless function
public class DestinationListControllerTest extends ApplicationTest {

        private Parent root;

        private TextArea destinationText;
        private Button addButton;
        private Button removeButton;
        private Button nameButton;
        private Button ratingButton;
        private Label feedbackLabel;
        private ListView<String> listView;

        private WireMockServer wireMockServer;

        /**
         * Enables headless testing
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

        @AfterAll
        public void stopWireMockServer() {
                wireMockServer.stop();
        }

        /**
         * Initializes FXML elements
         */
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
         * Initializes GUI
         */
        @Override
        public void start(Stage stage) throws IOException {

                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destinationList.fxml"));
                root = fxmlLoader.load();
                stage.setScene(new Scene(root));
                stage.show();

        }

        /**
         * Tests if listiew is properly updated with data from the mock server
         * <p>
         * This tests that the stored DestinationList copy is correctly used to display destinations
         */
        @Test
        public void testListViewUpdated() {
                assertEquals("[Costa Rica, Finland★★★★, Norway★★]", listView.getItems().toString());
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

                // listView should be updated with our new destination
                assertEquals("Helsinki", listView.getItems().get(3));

                // empty input
                clickOn(addButton);

                assertEquals("", destinationText.getText());
                assertEquals("", feedbackLabel.getText());

                // invalid input
                String invalidInput = "51*@4´,a#";
                clickOn(destinationText).write(invalidInput);

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

                // listView should be unchanged:
                assertEquals("[Costa Rica, Finland★★★★, Norway★★]", listView.getItems().toString());

        }

        /**
         * Tests if the remove button works as intended
         * <p>
         * This also implicitly tests that currentDestination field is updated correctly
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

                // listView should be updated:
                assertEquals("[Costa Rica, Finland★★★★]", listView.getItems().toString());


                // clicking remove again
                clickOn(removeButton);

                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

                // clicking on listView and clicking remove
                clickOn(listView);
                clickOn(removeButton);

                assertEquals("Please select a destination you would like to remove", feedbackLabel.getText());

                // listView should be unchanged:
                assertEquals("[Costa Rica, Finland★★★★]", listView.getItems().toString());

        }

        /**
         * Tests if sorting works as intended
         * <p>
         * This test depends on core.
         */
        @Test
        public void testSortDestinations() {
                // sorting by rating
                clickOn(ratingButton);
                assertEquals("[Finland★★★★, Norway★★, Costa Rica]", listView.getItems().toString());

                // sorting by name
                clickOn(nameButton);
                assertEquals("[Costa Rica, Finland★★★★, Norway★★]", listView.getItems().toString());

        }

}