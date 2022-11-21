package travelu.localpersistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import travelu.core.DestinationList;
import travelu.core.DateInterval;
import travelu.core.Destination;

/**
 * Tests for TraveluHandler class
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TraveluHandlerTest {

    private DestinationList destinationList;
    private Destination sweden;
    private Destination sanMarino;
    private Destination portugal;
    private DateInterval dateInterval = new DateInterval();

    /**
     * Create Destination objects, and add to DestinationList
     */
    @BeforeEach
    public void setup() {
        // Create empty destinationList
        destinationList = new DestinationList();

        // Create destinations
        sweden = new Destination("Sweden", dateInterval, 2, null, null);
        sanMarino = new Destination("San Marino", dateInterval, 2, null, null);
        portugal = new Destination("Portugal", dateInterval, 3, null, null);

        // Add two of the destinations to destinationList, third will be used
        destinationList.addDestination(sweden);
        destinationList.addDestination(sanMarino);
    }

    /**
     * Clear files after all tests.
     * <p>
     * CurrentDestinationName.json will contain an empty string.
     * <p>
     * DestinationList.json will contain a DestinationList object with an empty
     * destinations field.
     */
    @AfterAll
    public void tearDown() {
        try {
            TraveluHandler.clearDestinationName();
            TraveluHandler.clearDestinationList();
        } catch (Exception e) {
            fail("Failed to clear files");
        }
    }

    /**
     * Tests if DestinationList from JSON file is equal to DestinationList, and if
     * it is still equal
     * despite adding new Destination objects
     * <p>
     * Checks if FileNotFoundException gets thrown if file doesn't exist
     */
    @Test
    public void testWriteAndReadWhenAdding() {
        // Wrapped in try/catch statement to fail if IOException is thrown
        try {
            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");

            assertEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

            destinationList.addDestination(portugal);
            assertNotEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");

            assertEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

        } catch (IOException ioe) {
            fail("Error when reading/writing from/to file");
        }

    }

    /**
     * Tests if JSON file is equal to DestinationList when removing Destination
     * objects
     *
     */
    @Test
    public void testWriteAndReadWhenRemoving() {
        destinationList.removeDestination("Sweden");
        // Wrapped in try/catch statement to fail if IOException is thrown
        try {
            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");

            assertEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

            destinationList.removeDestination("San Marino");

            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");

            assertTrue(
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames().isEmpty());

        } catch (IOException ioe) {
            fail("Error when reading/writing from/to file");
        }
    }

    /**
     * Tests basic writing/reading current destination name
     */
    @Test
    public void testWriteAndReadCurrentDestinationName() {
        // Wrapped in try/catch statement to fail if IOException is thrown
        try {
            // Write "Norway" to testCurrentDestinationName.json
            TraveluHandler.writeJSON("Norway", "testCurrentDestinationName.json");
            
            // Assert that "Norway" is read from testCurrentDestinationName.json
            assertEquals("Norway",
                    TraveluHandler.readCurrentDestinationNameJSON("testCurrentDestinationName.json"));
        } catch (IOException ioe) {
            fail("Error when reading/writing from/to file");
        }
    }

}
