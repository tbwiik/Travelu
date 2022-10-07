package travelu.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    private DestinationList destinationList;

    private Destination norway;
    private String name;
    private HashMap<Date, Date> date;
    private Integer ranking;
    private List<String> activities;
    private String comment;

    private Destination buenosAires;
    private List<Destination> newDestinations;

    @BeforeEach
    public void setUp() {
        destinationList = new DestinationList();

        newDestinations = new ArrayList<>();

        name = "Norway";
        date = new HashMap<Date, Date>();
        ranking = 2;
        activities = new ArrayList<>();
        comment = null;

        norway = new Destination(name, date, ranking, activities, comment);
        buenosAires = new Destination("Buenos Aires", new HashMap<Date, Date>(), 2, null, null);

        newDestinations.add(new Destination("Spain", new HashMap<Date, Date>(), 4, null, null));
        newDestinations.add(buenosAires);
        newDestinations.add(new Destination("Turkey", new HashMap<Date, Date>(), 5, null, null));
        newDestinations.add(new Destination("Sweden", new HashMap<Date, Date>(), 1, null, null));
        newDestinations.add(norway);

        for (Destination destination : newDestinations) {
            destinationList.addDestination(destination);
        }
    }

    @Test
    public void testGetDestinationCopyByName() {

        // Compare two destination objects
        // Check if copy work as expected
        assertEquals(norway.getName(), name);
        assertEquals(norway.getDate(), date);
        assertEquals(norway.getRanking(), ranking);
        assertEquals(norway.getActivities(), activities);
        assertEquals(norway.getComment(), comment);

        assertThrows(IllegalArgumentException.class, () -> destinationList.getDestinationCopyByName("Does not exist"));

        assertThrows(IllegalArgumentException.class, () -> destinationList.getDestinationCopyByName(null));
    }

    @Test
    public void testGetDestinationNames() {
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("Spain");
        expectedNames.add("Buenos Aires");
        expectedNames.add("Turkey");
        expectedNames.add("Sweden");

        assertNotEquals(expectedNames, destinationList.getDestinationNames());

        expectedNames.add("Norway");

        assertEquals(expectedNames, destinationList.getDestinationNames());

        expectedNames.remove("Norway");
        expectedNames.add("Norway");

        assertEquals(expectedNames, destinationList.getDestinationNames());

        expectedNames.remove("Norway");
        expectedNames.add("NorWay");

        assertNotEquals(expectedNames, destinationList.getDestinationNames());
    }

    @Test
    public void testContainsDestination() {
        String norwayString = "Norway";
        String swedenString = "Sweden";
        String spainString = "spAin";
        String buenosAiresString = "Buenos Aires";
        String turkeyString = "TuRkEy";

        assertTrue(destinationList.containsDestination(norwayString));
        assertTrue(destinationList.containsDestination(swedenString));
        assertTrue(destinationList.containsDestination(spainString));
        assertTrue(destinationList.containsDestination(buenosAiresString));
        assertTrue(destinationList.containsDestination(turkeyString));

        String invalidString = "Does not exist";

        assertFalse(destinationList.containsDestination(invalidString));

        assertThrows(IllegalArgumentException.class, () -> destinationList.containsDestination(null));
    }

    @Test
    public void testAddDestination() {
        assertEquals(newDestinations, destinationList.getList());

        assertThrows(IllegalArgumentException.class, () -> destinationList.addDestination(null));
    }

    @Test
    public void testRemoveDestination() {
        newDestinations.remove(norway);
        destinationList.removeDestination("Norway");

        assertEquals(newDestinations, destinationList.getList());

        newDestinations.remove(buenosAires);
        destinationList.removeDestination("Buenos Aires");

        assertEquals(newDestinations, destinationList.getList());

        assertThrows(IllegalArgumentException.class, () -> destinationList.removeDestination("Norway"));

        assertThrows(IllegalArgumentException.class, () -> destinationList.removeDestination(null));
    }

}