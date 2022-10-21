package travelu.core;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        dateInterval1 = new DateInterval(31, 12, 1999, 01, 01, 2000);
        dateInterval2 = new DateInterval(new int[] {31,12,1999}, new int[]{01,01,2000});
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

        assertEquals(Arrays.toString(dateInterval1.getStartDate()), Arrays.toString(dateInterval2.getStartDate()));

        assertEquals("[31, 12, 1999]", Arrays.toString(dateInterval1.getStartDate()));
        assertNotEquals("[30, 11, 2021]", Arrays.toString(dateInterval1.getStartDate()));

        dateInterval1.setStartDate(new int[]{20,01,2000});
        assertNotEquals("[31, 12, 1999]", Arrays.toString(dateInterval1.getStartDate()));
        assertEquals("[20, 1, 2000]", Arrays.toString(dateInterval1.getStartDate()));

        
        assertEquals(Arrays.toString(dateInterval1.getEndDate()), Arrays.toString(dateInterval2.getEndDate()));
        assertEquals("[1, 1, 2000]", Arrays.toString(dateInterval2.getEndDate()));

        dateInterval2.setEndDate(new int[]{31,02,2000});
        assertEquals("[31, 2, 2000]", Arrays.toString(dateInterval2.getEndDate()));
    }

}
