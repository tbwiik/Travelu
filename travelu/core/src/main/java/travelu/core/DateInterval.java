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

        startDate = new int[3];
        startDate[0] = startDay;
        startDate[1] = startDay;
        startDate[2] = startDay;

        endDate = new int[3];
        endDate[0] = endDay;
        endDate[1] = endMonth;
        endDate[2] = endYear;

    }

    public int[] getStartDate() {
        return startDate;
    }

    public int[] getEndDate() {
        return endDate;
    }

    public void setStartDate(int[] startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(int[] endDate) {
        this.endDate = endDate;
    }

}
