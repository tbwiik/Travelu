package travelu.core;

/**
 * Date-interval, from-to, because problems with filewriting using pair and
 * date-object
 */
public class DateInterval {

    private int[] startDate;
    private int[] endDate;

    /**
     * Create DateInterval with two int-arrays
     * 
     * @param startDate containing day, month, year
     * @param endDate   containing day, month, year
     */
    public DateInterval(int[] startDate, int[] endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Create DateInterval with every value specified
     * 
     * @param startDay
     * @param startMonth
     * @param startYear
     * @param endDay
     * @param endMonth
     * @param endYear
     */
    public DateInterval(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {

        startDate = new int[] { startDay, startMonth, startYear };
        endDate = new int[] { endDay, endMonth, endYear };

    }

    /**
     * Create copy of DateInterval
     * 
     * @param dateInterval object
     */
    public DateInterval(DateInterval dateInterval) {
        this.startDate = dateInterval.getStartDate();
        this.endDate = dateInterval.getEndDate();
    }

    public int[] getStartDate() {
        return startDate.clone();
    }

    public int[] getEndDate() {
        return endDate.clone();
    }

    public void setStartDate(int[] startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(int[] endDate) {
        this.endDate = endDate;
    }

}
