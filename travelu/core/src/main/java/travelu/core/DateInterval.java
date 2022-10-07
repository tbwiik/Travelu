package travelu.core;

/**
 * Date-intervall, from-to, because problems with filewriting using pair and
 * date-object
 */
public class DateInterval {

    private int startDay, startMonth, startYear;
    private int endDay, endMonth, endYear;

    public DateInterval(int startDate, int startMonth, int startYear, int endDate, int endMonth, int endYear) {
        this.startDay = startDate;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endDay = endDate;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public int[] getStartDate() {

        int[] startDate = new int[3];

        startDate[0] = startDay;
        startDate[1] = startMonth;
        startDate[2] = startYear;

        return startDate;
    }

    public int[] getEndDate() {

        int[] endDate = new int[3];

        endDate[0] = endDay;
        endDate[1] = endMonth;
        endDate[2] = endYear;

        return endDate;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setStartDate(int startDay, int startMonth, int startYear) {
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
    }

    public void setEndDate(int endDay, int endMonth, int endYear) {
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateInterval other = (DateInterval) obj;
        if (startDay != other.startDay)
            return false;
        if (startMonth != other.startMonth)
            return false;
        if (startYear != other.startYear)
            return false;
        if (endDay != other.endDay)
            return false;
        if (endMonth != other.endMonth)
            return false;
        if (endYear != other.endYear)
            return false;
        return true;
    }

}
