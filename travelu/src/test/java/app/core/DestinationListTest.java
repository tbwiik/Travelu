package app.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    private Destination norway;
    private Destination buenosAires;
    private List<Destination> newDestinations;

    @BeforeEach
    public void setUp() {
        destinations = new DestinationList();

        newDestinations = new ArrayList<>();

        norway = new Destination("Norway", new HashMap<Date, Date>(), 2, null, null);
        buenosAires = new Destination("Buenos Aires", new HashMap<Date, Date>(), 2, null, null);

        newDestinations.add(new Destination("Spain", new HashMap<Date, Date>(), 4, null, null));
        newDestinations.add(buenosAires);
        newDestinations.add(new Destination("Turkey", new HashMap<Date, Date>(), 5, null, null));
        newDestinations.add(new Destination("Sweden", new HashMap<Date, Date>(), 1, null, null));
        newDestinations.add(norway);

        for (Destination destination : newDestinations) {
            destinations.addDestination(destination);
        }
    }

    @Test
    public void getDestinationByName() {
        assertEquals(norway, destinations.getDestinationByName("Norway"));
        assertEquals(buenosAires, destinations.getDestinationByName("Buenos Aires"));

        assertThrows(IllegalArgumentException.class, () -> destinations.getDestinationByName("Does not exist"));

        assertThrows(IllegalArgumentException.class, () -> destinations.getDestinationByName(null));
    }

    @Test
    public void getDestinationNames() {
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Spain");
        expectedNames.add("Buenos Aires");
        expectedNames.add("Turkey");
        expectedNames.add("Sweden");

        assertNotEquals(expectedNames, destinations.getDestinationNames());

        expectedNames.add("Norway");

        assertEquals(expectedNames, destinations.getDestinationNames());

        expectedNames.add("Does not exist");

        assertNotEquals(expectedNames, destinations.getDestinationNames());
    }

    @Test
    public void testAddDestination() {
        assertEquals(newDestinations, destinations.getList());

        assertThrows(IllegalArgumentException.class, () -> destinations.addDestination(null));
    }

    @Test
    public void testRemoveDestination() {
        newDestinations.remove(norway);
        destinations.removeDestination("Norway");

        assertEquals(newDestinations, destinations.getList());

        newDestinations.remove(buenosAires);
        destinations.removeDestination("Buenos Aires");

        assertEquals(newDestinations, destinations.getList());

        assertThrows(IllegalArgumentException.class, () -> destinations.removeDestination("Norway"));

        assertThrows(IllegalArgumentException.class, () -> destinations.removeDestination(null));
    }

}
