package travelu.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
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
        dateInterval = new DateInterval(17, 11, 2021, 13, 12, 2021);
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
        assertEquals(Arrays.toString(dateInterval.getStartDate()), Arrays.toString(destination.getDateInterval().getStartDate()));
        assertEquals(Arrays.toString(dateInterval.getEndDate()), Arrays.toString(destination.getDateInterval().getEndDate()));
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
