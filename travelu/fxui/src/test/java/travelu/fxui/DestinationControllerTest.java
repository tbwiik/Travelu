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

        // getting current destination
        stubFor(get(urlEqualTo("/api/v1/entries/currentDestination"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                                "{\"name\": \"Finland\",\"dateInterval\": {\"arrivalDate\": null,\"departureDate\": null},\"rating\": 4,\"activities\": [\"Went to sauna\", \"Learned finish\"],\"comment\": \"I traveled to Finland and I went to the sauna. It was very hot.\"}")));

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
     * Tests if DestinationList works as intended
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("destination.fxml"));
        root = fxmlLoader.load();
        destinationController = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();

    }

}