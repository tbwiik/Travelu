package travelu.integrationtests;

import travelu.client.Client;
import travelu.core.DateInterval;
import travelu.core.Destination;
import travelu.core.DestinationList;
import travelu.restserver.TraveluApplication;
import travelu.restserver.TraveluController;
import travelu.restserver.TraveluService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { TraveluController.class, TraveluApplication.class, TraveluService.class })
public class AppIT extends ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TraveluController controller;

    private Client client;

    // TODO: change methods to impact test JSON files

    /**
     * initialize client for server communication and clear destination list
     */
    @BeforeEach
    public void setUp() throws Exception {
        client = new Client("http://localhost", port);

        // remove all destinations from destinationlist
        clearDestinations();
    }

    /**
     * remove all destinations from destinationlist
     */
    private void clearDestinations() {

        try {
            for (Destination destination : client.getDestinationList().getList()) {
                client.removeDestination(destination.getName());
            }
        } catch (Exception e) {
            fail("Could not remove destinations");
        }
    }

    /**
     * test if initialisation works
     */
    @Test
    public void testApp() throws Exception {
        assertNotNull(controller);
        assertNotNull(client);
    }

    /**
     * test if storing current destination works
     */
    @Test
    public void testStoreCurrentDestination() {

        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }
    }

    /**
     * test if adding and removing a destination works
     */
    @Test
    public void testAddAndRemoveDestination() {

        List<String> activities = new ArrayList<>();
        activities.add("See volcanoes");
        activities.add("Dance hula with locals");
        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, activities,
                "I went to Hawaii and it was great!");

        // adding a destination
        try {
            client.addDestination(hawaii);
        } catch (Exception e) {
            fail("Could not add destination");
        }

        // removing the same destination
        try {
            client.removeDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not remove destination");
        }

        // TODO: client.removeDestination("Hawaii") should throw an exception:
        // assertThrows(NoSuchElementException.class, () -> {
        // client.removeDestination("Hawaii");
        // });
    }

    /**
     * test if adding and removing an activity works
     */
    @Test
    public void testAddAndRemoveActivity() {

        // choosing which destination to add activity to
        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        // adding an activity
        try {
            client.addActivity("Climb a volcano");
        } catch (Exception e) {
            fail("Could not add activity");
        }

        // removing the same activity
        try {
            client.removeActivity("Climb a volcano");
        } catch (Exception e) {
            fail("Could not remove activity");
        }

        // TODO: client.removeActivity("Climb a volcano") should throw an exception:
        // assertThrows(NoSuchElementException.class, () -> {
        // client.removeActivity("Climb a volcano");
        // });
    }

    /**
     * test if setting rating works
     */
    @Test
    public void testSetRating() {

        // choosing which destination to set rating for
        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        // setting rating
        try {
            client.setRating(5);
        } catch (Exception e) {
            fail("Could not set rating");
        }
    }

    /**
     * test if setting arrival date works
     */
    @Test
    public void testSetArrivalDate() {

        // choosing which destination to set arrival date for
        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        // setting arrival date
        try {
            client.setArrivalDate("03/03/2020");
        } catch (Exception e) {
            fail("Could not set arrival date");
        }
    }

    /**
     * test if setting departure date works
     */
    @Test
    public void testSetDepartureDate() {

        // choosing which destination to set departure date for
        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        // setting departure date
        try {
            client.setDepartureDate("11/03/2019");
        } catch (Exception e) {
            fail("Could not set departure date");
        }
    }

    /**
     * test if updating comment works
     */
    @Test
    public void testUpdateComment() {

        // choosing which destination to update comment for
        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        // updating comment
        try {
            client.updateComment("I went to Hawaii and it was great!");
        } catch (Exception e) {
            fail("Could not update comment");
        }
    }

    /**
     * test if getting destination list works
     */
    @Test
    public void testGetDestinationList() {

        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, new ArrayList<>(),
                "I went to Hawaii and it was great!");

        Destination japan = new Destination("Japan", new DateInterval(), 3, new ArrayList<>(),
                "I went to Japan and it was cool!");

        Destination france = new Destination("France", new DateInterval(), 3, new ArrayList<>(),
                "I went to France and it was fun!");

        // adding destinations
        try {
            client.addDestination(hawaii);
            client.addDestination(japan);
            client.addDestination(france);
        } catch (Exception e) {
            fail("Could not add destinations");
        }

        try {
            // checking if list from client contains the correct destinations
            DestinationList destinationList = client.getDestinationList();
            assertEquals(destinationList.getList(), client.getDestinationList().getList());
        } catch (Exception e) {
            fail("Could not get destination list");
        }
    }

    /**
     * test if getting a destination works
     */
    @Test
    public void testGetDestination() {

        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, new ArrayList<>(),
                "I went to Hawaii and it was great!");

        // adding destination
        try {
            client.addDestination(hawaii);
        } catch (Exception e) {
            fail("Could not add destination");
        }

        // checking if destination from client is the same as the one added
        try {
            Destination destinationFromName = client.getDestination("Hawaii");
            assertEquals(destinationFromName, client.getDestination("Hawaii"));
        } catch (Exception e) {
            fail("Could not get destination");
        }
    }

    /**
     * test if setting arrival date works
     */
    @Test
    public void testGetCurrentDestination() {

        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, new ArrayList<>(),
                "I went to Hawaii and it was great!");

        // adding destination
        try {
            client.addDestination(hawaii);
        } catch (Exception e) {
            fail("Could not add destination");
        }

        // choosing a destination which we are going to get
        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        // checking if destination from client is the same as the one added
        try {
            Destination currentDestination = client.getDestination();
            assertEquals(hawaii, currentDestination);
        } catch (Exception e) {
            fail("Could not get current destination");
        }
    }
}
