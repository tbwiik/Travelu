package travelu.fxutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import travelu.core.DestinationList;
import travelu.core.DateInterval;
import travelu.core.Destination;

/**
 * Tests for TraveluHandler class
 */
public class TraveluHandlerTest {

    private DestinationList destinationList;
    private Destination sweden;
    private Destination sanMarino;
    private Destination portugal;
    private DateInterval dateInterval = new DateInterval(new int[]{01,01,2021}, new int[]{14,02,2021});
    private TraveluHandler traveluHandler = new TraveluHandler();

    /**
     * Create Destination objects, and add to DestinationList
     */
    @BeforeEach
    public void setup() {
        destinationList = new DestinationList();

        sweden = new Destination("Sweden", dateInterval, 2, null, null);
        sanMarino = new Destination("San Marino", dateInterval, 2, null, null);
        portugal = new Destination("Portugal", dateInterval, 3, null, null);

        destinationList.addDestination(sweden);
        destinationList.addDestination(sanMarino);
    }

    /**
     * Tests if JSON file is equal to DestinationList, and if it is still equal despite adding new Destination objects
     * <p>
     * Checks if FileNotFoundException gets thrown if file doesn't exist
     * @throws IOException
     */
    @Test
    public void testWriteToFileWhenAdding() throws IOException {
        traveluHandler.writeJSON(destinationList, "testDestinationList.json");
        assertEquals(destinationList.getDestinationNames(),
                traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

        destinationList.addDestination(portugal);
        assertNotEquals(destinationList.getDestinationNames(),
                traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

        traveluHandler.writeJSON(destinationList, "testDestinationList.json");
        assertEquals(destinationList.getDestinationNames(),
                traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

        assertThrows(FileNotFoundException.class, () -> {
            traveluHandler.readDestinationListJSON("noExistingFile.json");
        });
    }

    /**
     * Tests if JSON file is equal to DestinationList when removing Destination objects
     * @throws IOException
     */
    @Test
    public void testWriteToFileWhenRemoving() throws IOException {
        destinationList.removeDestination("Sweden");
        traveluHandler.writeJSON(destinationList, "testDestinationList.json");
        assertEquals(destinationList.getDestinationNames(),
                traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

        destinationList.removeDestination("San Marino");
        traveluHandler.writeJSON(destinationList, "testDestinationList.json");
        assertTrue(traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames().isEmpty());
    }

}
