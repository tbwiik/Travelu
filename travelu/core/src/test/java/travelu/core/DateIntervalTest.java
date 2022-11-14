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

    private DateInterval dateInterval1;
    private DateInterval dateInterval2;
    private DateInterval empytInterval;

    /**
     * Create two DateInterval objects
     */
    @BeforeEach
    public void setup() {
        dateInterval1 = new DateInterval();
        dateInterval2 = new DateInterval();
        empytInterval = null;
    }

    /**
     * Checks if IllegalArgumentException gets thrown if DateInterval is null
     */
    @Test
    public void testDateIntervalCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new DateInterval(empytInterval));
    }

    /**
     * Compares the two DateInterval objects, and checks if changed dates work
     */
    @Test
    public void testStartAndEndDates() {

        assertNull(dateInterval1.getArrivalDate());
        assertNull(dateInterval1.getDepartureDate());

        dateInterval1.setArrivalDate("01/01/2019");
        assertEquals("01/01/2019", dateInterval1.getArrivalDate());

        dateInterval1.setDepartureDate("04/01/2019");
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        dateInterval2.setArrivalDate("01/01/2019");
        assertEquals("01/01/2019", dateInterval2.getArrivalDate());

        dateInterval2.setDepartureDate("04/01/2019");
        assertEquals("04/01/2019", dateInterval2.getDepartureDate());

        // check if date interval stays unchanged when arrival date is after departure
        // date
        assertThrows(IllegalStateException.class, () -> dateInterval1.setArrivalDate("01/01/2022"));
        assertEquals("01/01/2019", dateInterval1.getArrivalDate());

        // check if date interval stays unchanged when departure date is before arrival
        // date
        assertThrows(IllegalStateException.class, () -> dateInterval2.setDepartureDate("01/01/2018"));
        assertEquals("04/01/2019", dateInterval2.getDepartureDate());

    }

    /**
     * Tests dates that are obviously invalid
     */
    @Test
    public void testInvalidDates() {

        dateInterval1.setArrivalDate("01/01/2019");
        dateInterval1.setDepartureDate("04/01/2019");

        // check that date interval throws IllegalArgumentException, and stays unchanged
        // on invalid date input
        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("02/15/2019"));
        assertEquals("01/01/2019", dateInterval1.getArrivalDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setDepartureDate("32/01/2019"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        // Negative dates are invalid
        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("-31/01/2019"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("31/-01/2019"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("31/01/-2019"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("00/01/2019"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        // Dates before year 1000 are not allowed
        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("31/01/999"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        // Checks that the correct exception is thrown when inputting letters instead of
        // date
        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("aa/bb/cccc"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("a"));
        assertEquals("04/01/2019", dateInterval1.getDepartureDate());

    }

    /**
     * Tests correct handling of leap years
     */
    @Test
    public void testLeapYear() {
        dateInterval1.setArrivalDate("01/01/2019");
        dateInterval1.setDepartureDate("04/01/2019");

        // Check February 29th of non leap year
        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setDepartureDate("30/02/2019"));
        assertEquals("01/01/2019", dateInterval1.getArrivalDate());

        // Check February 29th of leap year
        assertDoesNotThrow(() -> dateInterval1.setDepartureDate("29/02/2020"));
        assertEquals("29/02/2020", dateInterval1.getDepartureDate());
    }

    @Test
    public void testCorrectEncapsulation() {

        DateInterval dateIntervalCopy = new DateInterval(dateInterval1);

        assertEquals(dateIntervalCopy.getArrivalDate(), dateInterval1.getArrivalDate());

        // making changes to arrival date in dateIntervalCopy should not impact
        // arrival date in dateInterval1
        dateIntervalCopy.setArrivalDate("01/01/2019");

        assertNotEquals(dateIntervalCopy.getArrivalDate(), dateInterval1.getArrivalDate());

    }

}
