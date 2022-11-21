package travelu.integrationtests;

import travelu.client.Client;
import travelu.client.ServerException;
import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.restserver.TraveluApplication;
import travelu.restserver.TraveluController;
import travelu.restserver.TraveluService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.testfx.framework.junit5.ApplicationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

// Set up server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// Defines how to load data
@ContextConfiguration(classes = { TraveluController.class, TraveluApplication.class, TraveluService.class })

// Enables @AfterAll function
@TestInstance(Lifecycle.PER_CLASS)
public class AppIntegrationTest extends ApplicationTest {

    /**
     * Port of local server
     */
    @LocalServerPort
    private int port;

    /**
     * Controller used in test
     */
    @Autowired
    private TraveluController controller;

    /**
     * Client used in test
     */
    private Client client;

    /**
     * Initialize client
     */
    @BeforeAll
    public void setUpAll() {
        client = new Client("http://localhost", port);
    }

    /**
     * Clear file before each test
     */
    @BeforeEach
    public void setUpEach() {
        clearDestinations();
    }

    /**
     * Clear files after all tests
     */
    @AfterAll
    public void tearDown() {

        // Remove all test destinations
        clearDestinations();
    }

    /**
     * Remove all destinations from destinationlist
     * <p>
     * Store empty string as current destination name
     */
    private void clearDestinations() {

        try {
            for (Destination destination : client.getDestinationList().getList()) {
                client.removeDestination(destination.getName());
            }
            client.storeCurrentDestinationName("");
        } catch (Exception e) {
            fail("Could not remove destinations");
        }
    }

    /**
     * Test if initialisation works
     */
    @Test
    public void testApp() throws Exception {
        assertNotNull(controller);
        assertNotNull(client);
    }

    /**
     * Test if storing current destination works
     */
    @Test
    public void testStoreCurrentDestination() {

        try {
            client.storeCurrentDestinationName("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }
    }

    /**
     * Test if adding and removing a destination works
     */
    @Test
    public void testAddAndRemoveDestination() {

        List<String> activities = new ArrayList<>();
        activities.add("See volcanoes");
        activities.add("Dance hula with locals");
        Destination holland = new Destination("Holland", new DateInterval(), 3, activities,
                "I went to Holland and it was great!");

        // Adding a destination
        try {
            client.addDestination(holland);
        } catch (Exception e) {
            fail("Could not add destination");
        }

        // Removing the same destination
        try {
            client.removeDestination("Holland");
        } catch (Exception e) {
            fail("Could not remove destination");
        }

        assertThrows(ServerException.class, () -> {
        client.removeDestination("Hawaii");
        });
    }

    /**
     * Helpermethod for setting up a destination
     * <p>
     * For testing of destination-functionality
     * <p>
     * <b>NB:</b> Initialized with name, but no other info
     * 
     * @param destinationName name of destination
     */
    private void setupDestination(final String destinationName) {

        // Set up dummy destination
        Destination destination = new Destination("Hawaii", new DateInterval(), 0, new ArrayList<>(),
                "");

        // Add destination to file
        try {
            client.addDestination(destination);
        } catch (Exception e) {
            fail("Could not add destination");
        }

        // Store destination as the chosen one
        try {
            client.storeCurrentDestinationName("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

    }

    /**
     * Test if adding and removing an activity works
     */
    @Test
    public void testAddAndRemoveActivity() {

        setupDestination("Hawaii");

        // Adding an activity
        try {
            client.addActivity("Climb a volcano");
        } catch (Exception e) {
            fail("Could not add activity");
        }

        // Removing the same activity
        try {
            client.removeActivity("Climb a volcano");
        } catch (Exception e) {
            fail("Could not remove activity");
        }

        assertThrows(ServerException.class, () -> {
        client.removeActivity("Climb a volcano");
        });
    }

    /**
     * Test if setting rating works
     */
    @Test
    public void testSetRating() {

        setupDestination("Hawaii");

        // Setting rating
        try {
            client.setRating(5);
        } catch (Exception e) {
            fail("Could not set rating");
        }
    }

    /**
     * Test if setting arrival date works
     */
    @Test
    public void testSetArrivalDate() {

        setupDestination("Hawaii");

        // Setting arrival date
        try {
            client.setArrivalDate("03/03/2020");
        } catch (Exception e) {
            fail("Could not set arrival date");
        }
    }

    /**
     * Test if setting departure date works
     */
    @Test
    public void testSetDepartureDate() {

        setupDestination("Hawaii");

        // Setting departure date
        try {
            client.setDepartureDate("11/03/2019");
        } catch (Exception e) {
            fail("Could not set departure date");
        }
    }

    /**
     * Test if updating comment works
     */
    @Test
    public void testUpdateComment() {

        setupDestination("Hawaii");

        // Updating comment
        try {
            client.updateComment("I went to Hawaii and it was great!");
        } catch (Exception e) {
            fail("Could not update comment");
        }
    }

    /**
     * Test if getting destination list works
     */
    @Test
    public void testGetDestinationList() {

        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, new ArrayList<>(),
                "I went to Hawaii and it was great!");

        Destination japan = new Destination("Japan", new DateInterval(), 3, new ArrayList<>(),
                "I went to Japan and it was cool!");

        Destination france = new Destination("France", new DateInterval(), 3, new ArrayList<>(),
                "I went to France and it was fun!");

        // Adding destinations
        try {
            client.addDestination(hawaii);
            client.addDestination(japan);
            client.addDestination(france);
        } catch (Exception e) {
            fail("Could not add destinations");
        }

        try {
            // Checking if list from client contains the correct destinations
            DestinationList destinationList = client.getDestinationList();
            assertEquals(destinationList.getDestinationNames(), client.getDestinationList().getDestinationNames());
        } catch (Exception e) {
            fail("Could not get destination list");
        }
    }

    /**
     * Test if getting a destination works
     */
    @Test
    public void testGetDestination() {

        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, new ArrayList<>(),
                "I went to Hawaii and it was great!");

        // Adding destination
        try {
            client.addDestination(hawaii);
        } catch (Exception e) {
            fail("Could not add destination");
        }

        // Checking if destination from client is the same as the one added
        try {
            Destination destinationFromName = client.getDestination("Hawaii");
            // Check that every field is equal in both destinations
            assertEquals(hawaii.getName(), destinationFromName.getName());
            assertEquals(hawaii.getDateInterval().getArrivalDate(), destinationFromName.getDateInterval().getArrivalDate());
            assertEquals(hawaii.getDateInterval().getDepartureDate(), destinationFromName.getDateInterval().getDepartureDate());
            assertEquals(hawaii.getRating(), destinationFromName.getRating());
            assertEquals(hawaii.getComment(), destinationFromName.getComment());
        } catch (Exception e) {
            fail("Could not get destination");
        }
    }

    /**
     * Test if setting arrival date works
     */
    @Test
    public void testGetCurrentDestination() {

        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, new ArrayList<>(),
                "I went to Hawaii and it was great!");

        // Adding destination
        try {
            client.addDestination(hawaii);
        } catch (Exception e) {
            fail("Could not add destination");
        }

        // Choosing a destination which we are going to get
        try {
            client.storeCurrentDestinationName("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        // Checking if destination from client is the same as the one added
        try {
            Destination currentDestination = client.getCurrentDestination();
            // Check that every field is equal in both destinations
            assertEquals(hawaii.getName(), currentDestination.getName());
            assertEquals(hawaii.getDateInterval().getArrivalDate(), currentDestination.getDateInterval().getArrivalDate());
            assertEquals(hawaii.getDateInterval().getDepartureDate(), currentDestination.getDateInterval().getDepartureDate());
            assertEquals(hawaii.getRating(), currentDestination.getRating());
            assertEquals(hawaii.getComment(), currentDestination.getComment());
        } catch (Exception e) {
            fail("Could not get current destination");
        }
    }
}
