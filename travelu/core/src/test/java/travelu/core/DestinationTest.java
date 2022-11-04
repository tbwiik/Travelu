package travelu.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private Integer ranking;
    private List<String> activities = new ArrayList<>();

    /**
     * Constructs a Destination object
     */
    @BeforeEach
    public void setUp() {
        name = "Sweden";
        dateInterval = new DateInterval();
        ranking = 3;

        activities.add("Skiing");
        activities.add("Circus");
        activities.add("Fancy dinner");

        comment = "Nice and cozy, but somewhat expensive dinner...";

        destination = new Destination(name, dateInterval, ranking, activities, comment);
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
     * Tests if you can add more comments
     */
    @Test
    public void testAddComment() {

        String com = destination.getComment();

        String addCom = "Remember to bring suncream";
        destination.addComment(addCom);
        assertEquals(com + "\n" + addCom, destination.getComment());
    }
}
