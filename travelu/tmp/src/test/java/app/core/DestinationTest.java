package app.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DestinationTest {

    private Destination destination;
    private String place, comment;
    private HashMap<Date, Date> date = new HashMap<>();
    private Integer ranking;
    private List<String> activites = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        place = "Sweden";
        date = null;
        ranking = 3;

        activites.add("Skiing");
        activites.add("Circus");
        activites.add("Fancy dinner");

        comment = "Nice and cozy, but somewhat expensive dinner...";

        destination = new Destination(place, date, ranking, activites, comment);
    }

    @Test
    public void testConstructor() {
        assertEquals(place, destination.getName());
        assertEquals(date, destination.getDate());
        assertEquals(activites, destination.getActivites());
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
