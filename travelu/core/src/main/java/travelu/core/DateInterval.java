package travelu.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Date-interval, from-to, because problems with filewriting using pair and
 * date-object
 */
public class DateInterval {

    private String startDate;
    private String endDate;

    /**
     * Create DateInterval with two int-arrays
     * 
     * @param startDate containing day, month, year
     * @param endDate   containing day, month, year
     */
    public DateInterval(String startDate, String endDate) {
        if (isValidDate(startDate)) {
            this.startDate = startDate;
        } else
            throw new IllegalArgumentException("Invalid startDate");

        if (isValidDate(endDate)) {
            this.endDate = endDate;
        } else
            throw new IllegalArgumentException("Invalid endDate");
    }

    /**
     * Create copy of DateInterval
     * 
     * @param dateInterval object
     */
    public DateInterval(DateInterval dateInterval) throws IllegalArgumentException {
        if (dateInterval == null)
            throw new IllegalArgumentException("dateInterval cannot be null");

        this.startDate = dateInterval.getStartDate();
        this.endDate = dateInterval.getEndDate();
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setStartDate(String startDate) {
        if (isValidDate(startDate))
            this.startDate = startDate;
        else
            throw new IllegalArgumentException("invalid start date");
    }

    public void setEndDate(String endDate) {
        if (isValidDate(endDate))
            this.endDate = endDate;
        else
            throw new IllegalArgumentException("invalid start date");
    }

    private boolean isValidDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // We allow the dates to be null
        if (dateString == null) {
            return true;
        }

    try{
        LocalDate.parse(dateString, formatter);
    }catch(
    Exception e){
        return false;
    }
    
    return true;
}

}
