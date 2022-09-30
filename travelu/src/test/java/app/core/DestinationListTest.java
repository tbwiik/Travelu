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

public class DestinationListTest {

    private DestinationList destinations;

    @BeforeEach
    public void setUp() {
        destinations = new DestinationList();
    }

    @Test
    public void getDestinationByName() {
        List<Destination> newDestinations = new ArrayList<>();

        Destination norway = new Destination("Norway", new HashMap<Date, Date>(), 2, null, null);
        Destination buenosAires = new Destination("Buenos Aires", new HashMap<Date, Date>(), 2, null, null);

        newDestinations.add(new Destination("Spain", new HashMap<Date, Date>(), 4, null, null));
        newDestinations.add(buenosAires);
        newDestinations.add(new Destination("Turkey", new HashMap<Date, Date>(), 5, null, null));
        newDestinations.add(new Destination("Sweden", new HashMap<Date, Date>(), 1, null, null));
        newDestinations.add(norway);

        for (Destination destination : newDestinations) {
            destinations.addDestination(destination);
        }

        assertEquals(norway, destinations.getDestinationByName("Norway"));
        assertEquals(buenosAires, destinations.getDestinationByName("Buenos Aires"));

        assertThrows(IllegalArgumentException.class, () -> destinations.getDestinationByName("Does not exist"));

        assertThrows(IllegalArgumentException.class, () -> destinations.getDestinationByName(null));
    }

    @Test
    public void testAddDestination() {

        assertTrue(destinations.getList().isEmpty());

        List<Destination> newDestinations = new ArrayList<>();
        newDestinations.add(new Destination("Spain", new HashMap<Date, Date>(), 4, null, null));
        newDestinations.add(new Destination("Greece", new HashMap<Date, Date>(), 2, null, null));
        newDestinations.add(new Destination("Turkey", new HashMap<Date, Date>(), 5, null, null));
        newDestinations.add(new Destination("Sweden", new HashMap<Date, Date>(), 1, null, null));
        newDestinations.add(new Destination("Norway", new HashMap<Date, Date>(), 2, null, null));

        for (Destination destination : newDestinations) {
            destinations.addDestination(destination);
        }

        assertEquals(newDestinations, destinations.getList());

        assertThrows(IllegalArgumentException.class, () -> destinations.addDestination(null));

    }

    @Test
    public void testRemoveDestination() {
        List<Destination> newDestinations = new ArrayList<>();

        Destination norway = new Destination("Norway", new HashMap<Date, Date>(), 2, null, null);
        Destination greece = new Destination("Greece", new HashMap<Date, Date>(), 2, null, null);

        newDestinations.add(new Destination("Spain", new HashMap<Date, Date>(), 4, null, null));
        newDestinations.add(greece);
        newDestinations.add(new Destination("Turkey", new HashMap<Date, Date>(), 5, null, null));
        newDestinations.add(new Destination("Sweden", new HashMap<Date, Date>(), 1, null, null));
        newDestinations.add(norway);

        for (Destination destination : newDestinations) {
            destinations.addDestination(destination);
        }

        newDestinations.remove(norway);
        destinations.removeDestination("Norway");

        assertEquals(newDestinations, destinations.getList());

        newDestinations.remove(greece);
        destinations.removeDestination("Greece");

        assertEquals(newDestinations, destinations.getList());

        assertThrows(IllegalArgumentException.class, () -> destinations.removeDestination("Norway"));

        assertThrows(IllegalArgumentException.class, () -> destinations.removeDestination(null));
    }

}
