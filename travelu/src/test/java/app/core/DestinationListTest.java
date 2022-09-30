package app.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.DestinationListController;

public class DestinationListTest {

    private DestinationListController destinationListController;
    private List<Destination> destinations;

    @BeforeEach
    public void setUp() {
        destinations = new ArrayList<>();
    }

    @Test
    public void testAddDestination() {
        Destination destination1 = new Destination("Spain", new HashMap<Date, Date>(), 4, null, null);
        Destination destination2 = new Destination("Greece", new HashMap<Date, Date>(), 2, null, null);
        Destination destination3 = new Destination("Turkey", new HashMap<Date, Date>(), 5, null, null);
        Destination destination4 = new Destination("Sweden", new HashMap<Date, Date>(), 1, null, null);
        Destination destination5 = new Destination("Norway", new HashMap<Date, Date>(), 2, null, null);

        assertTrue(destinations.isEmpty());

        List<Destination> newDestinations = new ArrayList<>();

        newDestinations.add(destination1);
        newDestinations.add(destination2);
        newDestinations.add(destination3);
        newDestinations.add(destination4);
        newDestinations.add(destination5);

        for (Destination destination : newDestinations) {
            destinations.add(destination);
        }

        assertEquals(destinations, newDestinations);

        assertThrows(IllegalArgumentException.class, () -> destinations.add(null));

    }

}
