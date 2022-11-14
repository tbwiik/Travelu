package travelu.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String change = "very fun";
        destination.setComment(change);
        assertEquals(change, destination.getComment());
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
        assertThrows(IllegalArgumentException.class, () -> destination.removeActivity(null));
        assertThrows(IllegalArgumentException.class, () -> destination.removeActivity("Fake activity"));
        // removeActivity is case sensitive
        assertThrows(IllegalArgumentException.class, () -> destination.removeActivity("circus"));

        // Tests for removing all elements in activities
        destination.removeActivity("Circus");
        destination.removeActivity("Fancy dinner");
        testActivities.remove("Circus");
        testActivities.remove("Fancy dinner");

        assertEquals(testActivities, destination.getActivities());
    }
}
