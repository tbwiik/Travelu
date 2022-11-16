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

    @BeforeEach
    public void setUp() throws Exception {
        client = new Client("http://localhost", port);

        // remove all destinations from destinationlist
        clearDestinations();
    }

    private void clearDestinations() {

        try {
            for (Destination destination : client.getDestinationList().getList()) {
                client.removeDestination(destination.getName());
            }
        } catch (Exception e) {
            fail("Could not remove destinations");
        }
    }

    @Test
    public void testApp() throws Exception {
        assertNotNull(controller);
        assertNotNull(client);
    }

    @Test
    public void testStoreCurrentDestination() {

        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }
    }

    @Test
    public void testAddAndRemoveDestination() {

        List<String> activities = new ArrayList<>();
        activities.add("See volcanoes");
        activities.add("Dance hula with locals");
        Destination hawaii = new Destination("Hawaii", new DateInterval(), 3, activities,
                "I went to Hawaii and it was great!");

        try {
            client.addDestination(hawaii);
        } catch (Exception e) {
            fail("Could not add destination");
        }

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

    @Test
    public void testAddAndRemoveActivity() {

        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        try {
            client.addActivity("Climb a volcano");
        } catch (Exception e) {
            fail("Could not add activity");
        }

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

    @Test
    public void testSetRating() {

        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        try {
            client.setRating(5);
        } catch (Exception e) {
            fail("Could not set rating");
        }
    }

    @Test
    public void testSetArrivalDate() {

        try {
            client.storeCurrentDestination("Hawaii");
        } catch (Exception e) {
            fail("Could not store current destination");
        }

        try {
            client.setArrivalDate("03/03/2020");
        } catch (Exception e) {
            fail("Could not set arrival date");
        }
    }
}
