package travelu.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for DestinationList class
 */
public class DestinationListTest {

    private DestinationList destinationList;

    private Destination norway;
    private String name;
    private DateInterval dateInterval;
    private int rating;
    private List<String> activities;
    private String comment;

    private Destination buenosAires;
    private List<Destination> newDestinations;

    /**
     * Add multiple Destination objects in DestinationList and in ArrayList
     */
    @BeforeEach
    public void setUp() {
        destinationList = new DestinationList();

        newDestinations = new ArrayList<>();

        name = "Norway";
        dateInterval = new DateInterval();
        rating = 3;
        activities = new ArrayList<>();
        comment = null;

        norway = new Destination(name, dateInterval, rating, activities, comment);
        buenosAires = new Destination("Buenos Aires", new DateInterval(), 2, null, null);

        newDestinations.add(new Destination("Spain", new DateInterval(), 4, null, null));
        newDestinations.add(buenosAires);
        newDestinations.add(new Destination("Turkey", new DateInterval(), 5, null, null));
        newDestinations.add(new Destination("Sweden", new DateInterval(), 1, null, null));
        newDestinations.add(norway);

        for (Destination destination : newDestinations) {
            destinationList.addDestination(destination);
        }
    }

    /**
     * Compares two destination objects, and check if copy works as expected
     * <p>
     * Checks if NoSuchElementException gets thrown if the name of Destination
     * doesn't exist or is null
     */
    @Test
    public void testGetDestinationCopyByName() {

        assertEquals(norway.getName(), name);
        assertEquals(norway.getDateInterval().getArrivalDate(),
                dateInterval.getArrivalDate());
        assertEquals(norway.getDateInterval().getDepartureDate(),
                dateInterval.getDepartureDate());
        assertEquals(norway.getRating(), rating);
        assertEquals(norway.getActivities(), activities);
        assertEquals(norway.getComment(), comment);

        assertThrows(NoSuchElementException.class, () -> destinationList.getDestinationCopyByName("Does not exist"));

        assertThrows(NoSuchElementException.class, () -> destinationList.getDestinationCopyByName(null));
    }

    /**
     * Tests if expectedNames is equal to destinationList using
     * getDestinationNames()
     */
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

    /**
     * Tests if given destination exists in DestinationList
     * <p>
     * Checks if IllegalArgumentException gets thrown if the given destination
     * doesn't exist
     */
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

    /**
     * Tests if the ArrayList and DestinationList are equal
     * 
     * @throws IllegalArgumentException if destinationName is null
     */
    @Test
    public void testAddDestination() {
        assertEquals(newDestinations, destinationList.getList());

        Destination newDestination = new Destination("Greenland", new DateInterval(), 1, null, null);
        
        // Adding a valid new destination to list
        newDestinations.add(newDestination);
        assertNotEquals(newDestinations, destinationList.getList());

        destinationList.addDestination(newDestination);
        assertEquals(newDestinations, destinationList.getList());

        // Adding an existing destination to list
        assertThrows(IllegalArgumentException.class, () -> destinationList.addDestination(norway));

        // Adding null to list
        assertThrows(IllegalArgumentException.class, () -> destinationList.addDestination(null));

        // Checks that list is unchanged after invalid inputs
        assertEquals(newDestinations, destinationList.getList());

    }

    /**
     * Tests if removeDestination removes destination
     * <p>
     * Checks if NoSuchElementException gets thrown if the destination doesn't
     * exist in list, and IllegalArgumentException is thrown if destination is null
     */
    @Test
    public void testRemoveDestination() {
        newDestinations.remove(norway);
        destinationList.removeDestination("Norway");

        assertEquals(newDestinations, destinationList.getList());

        newDestinations.remove(buenosAires);
        destinationList.removeDestination("Buenos Aires");

        assertEquals(newDestinations, destinationList.getList());

        assertThrows(NoSuchElementException.class, () -> destinationList.removeDestination("Not in list"));

        assertThrows(IllegalArgumentException.class, () -> destinationList.removeDestination(null));
    }

    /**
     * Tests if updateDestination sets correct values in destination
     */
    @Test
    public void testUpdateDestination() {
        
        Destination norwayCopy = destinationList.getDestinationCopyByName("Norway");
        norwayCopy.setComment("Changed comment");
        assertNotEquals(norwayCopy.getComment(), destinationList.getDestinationCopyByName("Norway").getComment());

        destinationList.updateDestination(norwayCopy);
        assertEquals(norwayCopy.getComment(), destinationList.getDestinationCopyByName("Norway").getComment());

        // null input
        assertThrows(IllegalArgumentException.class, () -> destinationList.updateDestination(null));

        // destination is not in list
        assertThrows(NoSuchElementException.class, () -> destinationList.updateDestination(new Destination("Not in list", new DateInterval(), 1, null, null)));

    }

    /**
     * Test if sorting by name works as intended
     */
    @Test
    public void testSortByName() {

        List<String> expectedList = new ArrayList<>();

        // adding destinations in alphabetical order
        expectedList.add("Buenos Aires");
        expectedList.add("Norway");
        expectedList.add("Spain");
        expectedList.add("Sweden");
        expectedList.add("Turkey");

        assertNotEquals(expectedList, destinationList.getDestinationNames());

        destinationList.sortByName();

        assertEquals(expectedList, destinationList.getDestinationNames());

        String dashDestinationName = "-Place";
        Destination dashDestination = new Destination(dashDestinationName, null, 5, null, null);
        expectedList.add(0, dashDestinationName);
        destinationList.addDestination(dashDestination);

        assertNotEquals(expectedList, destinationList.getDestinationNames());

        destinationList.sortByName();

        assertEquals(expectedList, destinationList.getDestinationNames());

        String lowerCaseDestinationName = "aa";
        Destination lowerCaseDestination = new Destination(lowerCaseDestinationName, null, 5, null, null);
        expectedList.add(1, lowerCaseDestinationName);

        destinationList.addDestination(lowerCaseDestination);

        assertNotEquals(expectedList, destinationList.getDestinationNames());

        destinationList.sortByName();

        assertEquals(expectedList, destinationList.getDestinationNames());
    }

    /**
     * Test if sorting by rating works as intended
     */
    @Test
    public void testSortByRating() {

        List<String> expectedList = new ArrayList<>();

        // adding destinations in order of rating
        expectedList.add("Turkey");
        expectedList.add("Spain");
        expectedList.add("Norway");
        expectedList.add("Buenos Aires");
        expectedList.add("Sweden");

        assertNotEquals(expectedList, destinationList.getDestinationNames());

        destinationList.sortByRating();

        assertEquals(expectedList, destinationList.getDestinationNames());

        String noStarsDestinationName = "France";
        Destination noStarsDestination = new Destination(noStarsDestinationName, null, 0, null, null);
        expectedList.add(noStarsDestinationName);

        assertNotEquals(expectedList, destinationList.getDestinationNames());

        destinationList.addDestination(noStarsDestination);
        destinationList.sortByRating();

        assertEquals(expectedList, destinationList.getDestinationNames());
    }

    /*
     * Test if encapsulation is correctly handled
     */
    @Test
    public void testCorrectEncapsulation() {

        Destination destinationCopy = destinationList.getDestinationCopyByName("Norway");

        assertEquals(destinationCopy.getComment(), norway.getComment());

        // making changes to comment on destinationCopy should not change
        // comment on norway
        destinationCopy.setComment("This should not change comment in destinationCopy");

        assertNotEquals(destinationCopy.getComment(), norway.getComment());

        List<Destination> destinationListCopy = destinationList.getList();

        assertEquals(destinationListCopy.size(), destinationList.getList().size());

        // making changes to destinationListCopy should not impact destinationList
        Destination extraDestination = new Destination("Extra destination", new DateInterval(), 3, null, null);
        destinationListCopy.add(extraDestination);

        assertNotEquals(destinationListCopy.size(), destinationList.getList().size());

        destinationListCopy.remove(extraDestination);

        assertEquals(destinationListCopy.size(), destinationList.getList().size());

        List<String> destinationNamesCopy = destinationList.getDestinationNames();

        assertEquals(destinationNamesCopy.size(), destinationList.getDestinationNames().size());

        destinationNamesCopy.add("Extra destination");

        // making changes to destinationList through getDestinationNames should not work
        destinationList.getDestinationNames().add("Extra destination");

        assertNotEquals(destinationNamesCopy.size(), destinationList.getDestinationNames().size());

    }

}
