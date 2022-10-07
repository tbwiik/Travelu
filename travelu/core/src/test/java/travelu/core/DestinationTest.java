package travelu.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DestinationTest {

    private Destination destination;
    private String name, comment;
    private DateInterval dateInterval;
    private Integer ranking;
    private List<String> activities = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        name = "Sweden";
        dateInterval = null;
        ranking = 3;

        activities.add("Skiing");
        activities.add("Circus");
        activities.add("Fancy dinner");

        comment = "Nice and cozy, but somewhat expensive dinner...";

        destination = new Destination(name, dateInterval, ranking, activities, comment);
    }

    @Test
    public void testConstructor() {
        assertEquals(name, destination.getName());
        assertEquals(dateInterval, destination.getDateIntervall());
        assertEquals(activities, destination.getActivities());
        assertEquals(comment, destination.getComment());
    }

    @Test
    public void testSetComment() {
        String change = "very fun";
        destination.setComment(change);
        assertEquals(change, destination.getComment());
    }

    @Test
    public void testAddComment() {

        String com = destination.getComment();

        String addCom = "Remember to bring suncream";
        destination.addComment(addCom);
        assertEquals(com + "\n" + addCom, destination.getComment());
    }
}
