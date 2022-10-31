package travelu.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Date-interval, from-to, because problems with filewriting using pair and
 * date-object
 */
public class DateInterval {

    private String arrivalDate;
    private String departureDate;

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

        this.arrivalDate = dateInterval.getArrivalDate();
        this.departureDate = dateInterval.getDepartureDate();
    }

    public String getArrivalDate() {
        return this.arrivalDate;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setArrivalDate(String arrivalDate) {
        if (isValidDatePair(arrivalDate, this.departureDate)){
            
            this.arrivalDate = arrivalDate;
        }
        else
            throw new IllegalArgumentException("invalid arrival date");
    }


    public void setDepartureDate(String departureDate) {
        if (isValidDatePair(arrivalDate, departureDate))
            this.departureDate = departureDate;
        else
            throw new IllegalArgumentException("invalid departure date");
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
 * @param arrivalDate - string
 * @param departureDate - string
 * @return - boolean representing whether this is a valid date pair
 */
private boolean isValidDatePair(String arrivalDate, String departureDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    if(isValidDate(arrivalDate, formatter) && isValidDate(departureDate, formatter)){
        LocalDate arrival = LocalDate.parse(arrivalDate, formatter);
        LocalDate departure = LocalDate.parse(departureDate, formatter);

        // departure should either be after arrival or the same day
        return departure.isAfter(arrival) || departure.isEqual(arrival);
    }
    // if one of the dates are null, return whether either of them are valid
    else if(arrivalDate == null || departureDate == null){
        return isValidDate(arrivalDate, formatter) || isValidDate(departureDate, formatter);
    }


    return false;
}   

}
