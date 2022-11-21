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
    private Destination buenosAires;

    private List<Destination> expectedDestinations;

    /**
     * Add multiple Destination objects in DestinationList and in ArrayList
     */
    @BeforeEach
    public void setUp() {
        destinationList = new DestinationList();

        expectedDestinations = new ArrayList<>();

        norway = new Destination("Norway", new DateInterval(), 3, new ArrayList<>(), "");
        buenosAires = new Destination("Buenos Aires", new DateInterval(), 2, null, "");

        expectedDestinations.add(norway);
        expectedDestinations.add(buenosAires);
        expectedDestinations.add(new Destination("Spain", new DateInterval(), 4, null, ""));
        expectedDestinations.add(new Destination("Turkey", new DateInterval(), 5, null, ""));
        expectedDestinations.add(new Destination("Sweden", new DateInterval(), 1, null, ""));

        for (Destination destination : expectedDestinations) {
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
        expectedNames.add("Norway");
        expectedNames.add("Buenos Aires");
        expectedNames.add("Spain");
        expectedNames.add("Turkey");

        assertNotEquals(expectedNames, destinationList.getDestinationNames());

        expectedNames.add("Sweden");

        assertEquals(expectedNames, destinationList.getDestinationNames());

        expectedNames.remove("Sweden");
        expectedNames.add("SwEden");

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
        // Checking valid destination names
        String norwayString = "Norway";
        assertTrue(destinationList.containsDestination(norwayString));

        String buenosAiresString = "Buenos Aires";
        assertTrue(destinationList.containsDestination(buenosAiresString));

        // The tests should result in true regardless of casing
        String spainString = "sPaIn";
        assertTrue(destinationList.containsDestination(spainString));

        // Any symbols or spaces added should result in false
        String turkeyString = " Turkey ";
        String swedenString = "Sweden-";
        assertFalse(destinationList.containsDestination(turkeyString));
        assertFalse(destinationList.containsDestination(swedenString));

        // A destination that doesn't exist should result in false
        String invalidString = "Does not exist";
        assertFalse(destinationList.containsDestination(invalidString));

        // Test if exception gets thrown if the given destination is null
        assertThrows(IllegalArgumentException.class, () -> destinationList.containsDestination(null));
    }

    /**
     * Check that two lists of destinations have identical content.
     * This method is useful because due to encapsulation we can not access the
     * actual destination objects of list.
     * Therefore we have to compare the fields of all destinations in lists to
     * ensure that they are equal.
     * 
     * @param list1 of destinations
     * @param list2 of destinations
     * @return true if lists are equal
     */
    private boolean listsAreEqual(List<Destination> list1, List<Destination> list2) {
        if (list1.size() != list2.size())
            return false;

        // Checks that every field of every Destination is the same in both lists
        for (int i = 0; i < list1.size(); i++) {
            Destination dest1 = list1.get(i);
            Destination dest2 = list2.get(i);
            DateInterval dest1DateInterval = dest1.getDateInterval();
            DateInterval dest2DateInterval = dest2.getDateInterval();
            String dest1ArrivalDate = dest1DateInterval.getArrivalDate() == null ? ""
                    : dest1DateInterval.getArrivalDate();
            String dest2ArrivalDate = dest2DateInterval.getArrivalDate() == null ? ""
                    : dest2DateInterval.getArrivalDate();
            String dest1Comment = dest1.getComment() == null ? "" : dest1.getComment();
            String dest2Comment = dest2.getComment() == null ? "" : dest2.getComment();

            if (!dest1.getName().equals(dest2.getName()))
                return false;
            if (dest1.getRating() != dest2.getRating())
                return false;
            if (!dest1ArrivalDate.equals(dest2ArrivalDate))
                return false;
            if (!dest1ArrivalDate.equals(dest2ArrivalDate))
                return false;
            if (!dest1Comment.equals(dest2Comment))
                return false;
            if (!dest1.getActivities().equals(dest2.getActivities()))
                return false;
        }
        return true;
    }

    /**
     * Tests if the ArrayList and DestinationList are equal
     * 
     * @throws IllegalArgumentException if destinationName is null
     */
    @Test
    public void testAddDestination() {
        assertTrue(listsAreEqual(expectedDestinations, destinationList.getList()));

        Destination newDestination = new Destination("Greenland", new DateInterval(), 1, null, null);

        // Adding a valid new destination to list
        expectedDestinations.add(newDestination);
        assertFalse(listsAreEqual(expectedDestinations, destinationList.getList()));

        destinationList.addDestination(newDestination);
        assertTrue(listsAreEqual(expectedDestinations, destinationList.getList()));

        // Adding an existing destination to list
        assertThrows(IllegalArgumentException.class, () -> destinationList.addDestination(norway));

        // Adding null to list
        assertThrows(IllegalArgumentException.class, () -> destinationList.addDestination(null));

        // Checks that list is unchanged after invalid inputs
        assertTrue(listsAreEqual(expectedDestinations, destinationList.getList()));
    }

    /**
     * Tests if removeDestination removes destination
     * <p>
     * Checks if NoSuchElementException gets thrown if the destination doesn't
     * exist in list, and IllegalArgumentException is thrown if destination is null
     */
    @Test
    public void testRemoveDestination() {
        expectedDestinations.remove(norway);

        assertFalse(listsAreEqual(expectedDestinations, destinationList.getList()));

        destinationList.removeDestination("Norway");

        assertTrue(listsAreEqual(expectedDestinations, destinationList.getList()));

        expectedDestinations.remove(buenosAires);
        assertFalse(listsAreEqual(expectedDestinations, destinationList.getList()));

        destinationList.removeDestination("Buenos Aires");
        assertTrue(listsAreEqual(expectedDestinations, destinationList.getList()));

        assertThrows(NoSuchElementException.class, () -> destinationList.removeDestination("Not in list"));

        assertThrows(IllegalArgumentException.class, () -> destinationList.removeDestination(null));

        // List should be unchanged after invalid input
        assertTrue(listsAreEqual(expectedDestinations, destinationList.getList()));
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
        assertThrows(NoSuchElementException.class, () -> destinationList
                .updateDestination(new Destination("Not in list", new DateInterval(), 1, null, null)));

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
