package travelu.fxutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import travelu.core.DestinationList;
import travelu.core.Destination;

public class TraveluHandlerTest {

    private DestinationList destinationList;
    private Destination sweden;
    private Destination sanMarino;
    private Destination portugal;

    private TraveluHandler traveluHandler = new TraveluHandler();

    @BeforeEach
    public void setup() {
        destinationList = new DestinationList();

        sweden = new Destination("Sweden", new HashMap<Date, Date>(), 2, null, null);
        sanMarino = new Destination("San Marino", new HashMap<Date, Date>(), 2, null, null);
        portugal = new Destination("Portugal", new HashMap<Date, Date>(), 3, null, null);

        destinationList.addDestination(sweden);
        destinationList.addDestination(sanMarino);
    }

    @Test
    public void testWriteToFile() throws IOException {
        traveluHandler.writeJSON(destinationList, "testDestinationList.json"); 
        assertEquals(destinationList.getDestinationNames(), traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());
        
        destinationList.addDestination(portugal);
        assertNotEquals(destinationList.getDestinationNames(), traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());

        traveluHandler.writeJSON(destinationList, "testDestinationList.json");
        assertEquals(destinationList.getDestinationNames(), traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());
        }

    @Test
    public void testWriteToFile2() throws IOException {
        destinationList.removeDestination("Sweden");
        traveluHandler.writeJSON(destinationList, "testDestinationList.json");
        assertEquals(destinationList.getDestinationNames(), traveluHandler.readDestinationListJSON("testDestinationList.json").getDestinationNames());
    }




}
