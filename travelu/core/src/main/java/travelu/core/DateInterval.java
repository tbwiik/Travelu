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

    public void setArrivalDate(String arrivalDate) throws IllegalArgumentException {
        
        checkDatePair(arrivalDate, this.departureDate);
        this.arrivalDate = arrivalDate;
    }


    public void setDepartureDate(String departureDate) throws IllegalArgumentException {
        checkDatePair(this.arrivalDate, departureDate);
        this.departureDate = departureDate;
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
 * 
 * @throws IllegalArgumentException - If the date pair is invalid
 */
private void checkDatePair(String arrivalDate, String departureDate) throws IllegalArgumentException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");
    boolean arrivalValid = isValidDate(arrivalDate, formatter);
    boolean departureValid = isValidDate(departureDate, formatter);


    if(arrivalValid && departureValid){
        LocalDate arrival = LocalDate.parse(arrivalDate, formatter);
        LocalDate departure = LocalDate.parse(departureDate, formatter);

        // departure should either be after arrival or the same day
        if(!departure.isAfter(arrival) && !departure.isEqual(arrival)){
            throw new IllegalArgumentException("Arrival date must be before departure date.");
        };
    }
    else if(!departureValid && departureDate != null){
        throw new IllegalArgumentException("Invalid departure date.");
    }
    else if(!arrivalValid && arrivalDate != null){
        throw new IllegalArgumentException("Invalid arrival date.");
    }
}   

}
