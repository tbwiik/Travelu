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
     * Create empty DateInterval
     */
    public DateInterval() {}

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
        if (isValidDatePair(startDate, this.endDate)){
            
            this.startDate = startDate;
        }
        else
            throw new IllegalArgumentException("invalid start date");
    }


    public void setEndDate(String endDate) {
        if (isValidDatePair(startDate, endDate))
            this.endDate = endDate;
        else
            throw new IllegalArgumentException("invalid end date");
    }

    private boolean isValidDate(String dateString, DateTimeFormatter formatter) {
    

        try{
            LocalDate.parse(dateString, formatter);
        }catch(
        Exception e){
            return false;
        }
        
        return true;
    }

/**
 * 
 * @param startDate - string
 * @param endDate - string
 * @return - boolean representing whether this is a valid date pair
 */
private boolean isValidDatePair(String startDate, String endDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    if(isValidDate(startDate, formatter) && isValidDate(endDate, formatter)){
        LocalDate arrival = LocalDate.parse(startDate, formatter);
        LocalDate departure = LocalDate.parse(endDate, formatter);

        // departure should either be after arrival or the same day
        return departure.isAfter(arrival) || departure.isEqual(arrival);
    }
    // if one of the dates are null, return whether either of them are valid
    else if(startDate == null || endDate == null){
        return isValidDate(startDate, formatter) || isValidDate(endDate, formatter);
    }


    return false;
}   

}
