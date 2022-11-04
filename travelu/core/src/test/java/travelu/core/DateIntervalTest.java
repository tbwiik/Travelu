package travelu.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

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
     * Create two DateInterval objects using two different methods
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

        dateInterval1.setDepartureDate("01/04/2019");
        assertEquals("01/04/2019", dateInterval1.getDepartureDate());

        dateInterval2.setArrivalDate("01/01/2019");
        assertEquals("01/01/2019", dateInterval2.getArrivalDate());

        dateInterval2.setDepartureDate("01/04/2019");
        assertEquals("01/04/2019", dateInterval2.getDepartureDate());

        // check if date interval stays unchanged on invalid date input
        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("15/02/2019"));
        assertEquals("01/01/2019", dateInterval1.getArrivalDate());

        assertThrows(IllegalArgumentException.class, () -> dateInterval2.setDepartureDate("01/32/2019"));
        assertEquals("01/04/2019", dateInterval2.getDepartureDate());

        // check if date interval stays unchanged when arrival date is after departure
        // date
        assertThrows(IllegalArgumentException.class, () -> dateInterval1.setArrivalDate("01/01/2022"));
        assertEquals("01/01/2019", dateInterval1.getArrivalDate());

        // check if date interval stays unchanged when departure date is before arrival
        // date
        assertThrows(IllegalArgumentException.class, () -> dateInterval2.setDepartureDate("01/01/2018"));
        assertEquals("01/04/2019", dateInterval2.getDepartureDate());

    }

}
