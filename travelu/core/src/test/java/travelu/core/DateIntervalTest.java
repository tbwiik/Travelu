package travelu.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for DateInterval class
 */
public class DateIntervalTest {

    private DateInterval dateInterval;

    /**
     * Create two DateInterval objects
     */
    @BeforeEach
    public void setup() {
        dateInterval = new DateInterval();
    }

    /**
     * Checks if IllegalArgumentException gets thrown if DateInterval is null
     */
    @Test
    public void testNullDateInterval() {
        assertThrows(IllegalArgumentException.class, () -> new DateInterval(null));
    }

    /**
     * Compares the two DateInterval objects, and checks if changed dates work
     */
    @Test
    public void testArrivalAndDepartureDates() {

        // Arrival and departure dates should be null before assignment
        assertNull(dateInterval.getArrivalDate());
        assertNull(dateInterval.getDepartureDate());

        // Check if inputting valid dates works
        dateInterval.setArrivalDate("01/01/2019");
        assertEquals("01/01/2019", dateInterval.getArrivalDate());

        dateInterval.setDepartureDate("04/01/2019");
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        dateInterval.setArrivalDate("29/11/2013");
        assertEquals("29/11/2013", dateInterval.getArrivalDate());

        dateInterval.setDepartureDate("15/04/2014");
        assertEquals("15/04/2014", dateInterval.getDepartureDate());

        // Check if date interval stays unchanged when arrival date is after departure
        // date
        assertThrows(IllegalStateException.class, () -> dateInterval.setArrivalDate("01/01/2022"));
        assertEquals("29/11/2013", dateInterval.getArrivalDate());

        // Check if date interval stays unchanged when departure date is before arrival
        // date
        assertThrows(IllegalStateException.class, () -> dateInterval.setDepartureDate("01/01/2009"));
        assertEquals("15/04/2014", dateInterval.getDepartureDate());

        dateInterval.setArrivalDate(null);
        // Dates are allowed to be null
        assertEquals(null, dateInterval.getArrivalDate());

    }

    /**
     * Tests dates that are obviously invalid
     */
    @Test
    public void testInvalidDates() {

        dateInterval.setArrivalDate("01/01/2019");
        dateInterval.setDepartureDate("04/01/2019");

        // Check that date interval throws IllegalArgumentException, and stays unchanged
        // on invalid date input
        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("02/15/2019"));
        assertEquals("01/01/2019", dateInterval.getArrivalDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval.setDepartureDate("32/01/2019"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        // Negative dates are invalid
        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("-31/01/2019"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("31/-01/2019"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("31/01/-2019"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("00/01/2019"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        // Dates before year 1000 are not allowed
        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("31/01/999"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        // Dates after year 9999 are not allowed
        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("31/01/10000"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        // Checks that the correct exception is thrown when inputting letters instead of
        // date
        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("aa/bb/cccc"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval.setArrivalDate("a"));
        assertEquals("04/01/2019", dateInterval.getDepartureDate());

    }

    /**
     * Tests correct handling of leap years
     */
    @Test
    public void testLeapYear() {
        dateInterval.setArrivalDate("01/01/2019");
        dateInterval.setDepartureDate("04/01/2019");

        // Check February 29th of non leap year
        assertThrows(IllegalArgumentException.class, () -> dateInterval.setDepartureDate("30/02/2019"));
        assertEquals("01/01/2019", dateInterval.getArrivalDate());

        // Check February 29th of leap year
        assertDoesNotThrow(() -> dateInterval.setDepartureDate("29/02/2020"));
        assertEquals("29/02/2020", dateInterval.getDepartureDate());
    }

    /**
     * Test if encapsulation is correctly handled
     */
    @Test
    public void testCorrectEncapsulation() {

        DateInterval dateIntervalCopy = new DateInterval(dateInterval);

        assertEquals(dateIntervalCopy.getArrivalDate(), dateInterval.getArrivalDate());

        // Making changes to arrival date in dateIntervalCopy should not impact
        // arrival date in dateInterval1
        dateIntervalCopy.setArrivalDate("01/01/2019");

        assertNotEquals(dateIntervalCopy.getArrivalDate(), dateInterval.getArrivalDate());

    }

}
