package travelu.localpersistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
    private DateInterval dateInterval = new DateInterval();

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
     * Tests if JSON file is equal to DestinationList, and if it is still equal
     * despite adding new Destination objects
     * <p>
     * Checks if FileNotFoundException gets thrown if file doesn't exist
     * 
     */
    @Test
    public void testWriteToFileWhenAdding() {
        try {
            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");
        } catch (IOException ioe) {
            fail("Error when writing to file");
        }

        try {
            assertEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());
        } catch (IOException ioe) {
            fail("Error when reading from file");
        }

        destinationList.addDestination(portugal);
        try {
            assertNotEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());
        } catch (IOException ioe) {
            fail("Error when reading from file");
        }

        try {
            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");
        } catch (IOException ioe) {
            fail("Error when writing to file");
        }
        
        try {
            assertEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());
        } catch (IOException ioe) {
            fail("Error when reading from file");
        }

        
        assertThrows(FileNotFoundException.class, () -> {
                TraveluHandler.readDestinationListJSON("noExistingFile.json");
        });
                    
        
    }

    /**
     * Tests if JSON file is equal to DestinationList when removing Destination
     * objects
     * 
     */
    @Test
    public void testWriteToFileWhenRemoving() {
        destinationList.removeDestination("Sweden");
        try{
            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");
        }  catch (IOException ioe){
            fail("Error when writing to file");
        }

        try{
            assertEquals(destinationList.getDestinationNames(),
                    TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());
        } catch (IOException ioe){
            fail("Error when reading from file");
        }

        destinationList.removeDestination("San Marino");

        try{
            TraveluHandler.writeJSON(destinationList, "testDestinationList.json");
        } catch (IOException ioe){
            fail("Error when writing to file");
        }


        try{
            assertTrue(TraveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames().isEmpty());
        } catch (IOException ioe){
            fail("Error when reading from file");
        }
    }

}
