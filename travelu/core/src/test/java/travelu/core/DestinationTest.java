package travelu.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for Destination class
 */
public class DestinationTest {

    private Destination destination;
    private String name, comment;
    private DateInterval dateInterval;
    private int rating;
    private List<String> activities = new ArrayList<>();

    /**
     * Constructs a Destination object
     */
    @BeforeEach
    public void setUp() {
        name = "Sweden";
        dateInterval = new DateInterval();
        rating = 3;

        activities.add("Skiing");
        activities.add("Circus");
        activities.add("Fancy dinner");

        comment = "Nice and cozy, but somewhat expensive dinner...";

        destination = new Destination(name, dateInterval, rating, activities, comment);
    }

    /**
     * Tests if the object has the same inputs
     */
    @Test
    public void testConstructor() {
        assertEquals(name, destination.getName());
        assertEquals(dateInterval.getArrivalDate(), destination.getDateInterval().getArrivalDate());
        assertEquals(dateInterval.getDepartureDate(), destination.getDateInterval().getDepartureDate());
        assertEquals(activities, destination.getActivities());
        assertEquals(comment, destination.getComment());
    }

    /**
     * Tests if comment is set to "change"
     */
    @Test
    public void testSetComment() {
        String comment = "very fun";
        destination.setComment(comment);
        assertEquals(comment, destination.getComment());

        // Test setting null as comment. This is allowed
        destination.setComment(null);
        assertEquals(null, destination.getComment());

        // Test setting empty string as comment. This is allowed
        destination.setComment("");
        assertEquals("", destination.getComment());

        // Test string with uncommon but legal characters
        comment = "!* ~/?+ . æøå";
        destination.setComment(comment);
        assertEquals(comment, destination.getComment());

    }

    /**
     * Tests adding activity to destination
     */
    @Test
    public void testAddActivity() {

        List<String> testActivities = new ArrayList<>();
        testActivities.add("Skiing");
        testActivities.add("Circus");
        testActivities.add("Fancy dinner");

        assertEquals(testActivities, destination.getActivities());

        // valid input
        destination.addActivity("Dance battle");
        assertNotEquals(testActivities, destination.getActivities());

        testActivities.add("Dance battle");
        assertEquals(testActivities, destination.getActivities());

        // should throw IllegalArgumentException if activity is null
        assertThrows(IllegalArgumentException.class, () -> destination.addActivity(null));

        // should throw IllegalArgumentException if activity is empty
        assertThrows(IllegalArgumentException.class, () -> destination.addActivity(""));

        // should throw IllegalArgumentException if activity is already in list
        assertThrows(IllegalArgumentException.class, () -> destination.addActivity("Skiing"));

    }

    /**
     * Tests removing activity
     */
    @Test
    public void testRemoveActivity() {

        List<String> testActivities = new ArrayList<>();
        testActivities.add("Skiing");
        testActivities.add("Circus");
        testActivities.add("Fancy dinner");

        assertEquals(testActivities, destination.getActivities());

        destination.removeActivity("Skiing");
        assertNotEquals(testActivities, destination.getActivities());

        testActivities.remove("Skiing");
        assertEquals(testActivities, destination.getActivities());

        // we do not allow removing elements that are not in activities list
        assertThrows(NoSuchElementException.class, () -> destination.removeActivity(null));
        assertThrows(NoSuchElementException.class, () -> destination.removeActivity(""));
        assertThrows(NoSuchElementException.class, () -> destination.removeActivity("Fake activity"));
        // removeActivity is case sensitive
        assertThrows(NoSuchElementException.class, () -> destination.removeActivity("circus"));

        // Tests for removing all elements in activities
        destination.removeActivity("Circus");
        destination.removeActivity("Fancy dinner");
        testActivities.remove("Circus");
        testActivities.remove("Fancy dinner");

        assertEquals(testActivities, destination.getActivities());
    }

    /**
     * Test setting rating. New rating must be between 1-5
     */
    @Test
    public void testSetRating() {
        // valid input
        int rating = 4;
        assertNotEquals(rating, destination.getRating());
        destination.setRating(rating);
        assertEquals(rating, destination.getRating());

        // invalid input
        assertThrows(IllegalArgumentException.class, () -> destination.setRating(6));
        assertThrows(IllegalArgumentException.class, () -> destination.setRating(0));
        assertThrows(IllegalArgumentException.class, () -> destination.setRating(-1));

        // check that rating is unchanged
        assertEquals(rating, destination.getRating());

    }

    /*
     * Test if encapsulation is correctly handled
     */
    @Test
    public void testCorrectEncapsulation() {

        Destination destinationCopy = new Destination(destination);

        // Comments should be equal at start
        assertEquals(destinationCopy.getComment(), destination.getComment());

        // Making changes to comment on destinationCopy should not impact
        // comment in destination
        destinationCopy.setComment("This should not change comment in destinationCopy");

        assertNotEquals(destinationCopy.getComment(), destination.getComment());

        // Create copy of date interval to test encapsulation here
        DateInterval dateIntervalCopy = destination.getDateInterval();
        assertEquals(dateIntervalCopy.getArrivalDate(), destination.getDateInterval().getArrivalDate());

        // Making changes to arrivalDate in dateIntervalCopy should not impact
        // dateInterval in destination
        dateIntervalCopy.setArrivalDate("01/01/2020");

        assertNotEquals(dateIntervalCopy.getArrivalDate(), destination.getDateInterval().getArrivalDate());

        List<String> activitiesCopy = destination.getActivities();

        assertEquals(activitiesCopy, destination.getActivities());
        assertEquals(3, destination.getActivities().size());

        activitiesCopy.add("Skateboarding");

        // making changes to activities through getActivities should not work
        destination.getActivities().add("Skateboarding");

        assertEquals(3, destination.getActivities().size());

        assertNotEquals(activitiesCopy, destination.getActivities());

    }
}
