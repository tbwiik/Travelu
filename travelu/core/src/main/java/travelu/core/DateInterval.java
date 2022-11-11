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

    /**
     * Checks that dateString represents a valid date with 3 parts: day, month and year
     * @param dateString on the format "dd/MM/yyyy". Formatting is strict, years before 1000 are not accepted
     * @return boolean
     */
    private boolean isValidDate(String dateString) {
        try{
            String[] dateArray = dateString.split("/");
            int day = Integer.parseInt(dateArray[0]);
            int month = Integer.parseInt(dateArray[1]);
            int year = Integer.parseInt(dateArray[2]);
            LocalDate.of(year, month, day);
            return dateArray.length == 3 && dateString.length() == 10;
        }catch(
        Exception e){
            return false;
        }
    }

/**
 * 
 * @param arrivalDate - string
 * @param departureDate - string
 * 
 * @throws IllegalArgumentException - If the date pair is invalid
 */
private void checkDatePair(String arrivalDate, String departureDate) throws IllegalArgumentException {
    
    boolean arrivalValid = isValidDate(arrivalDate);
    boolean departureValid = isValidDate(departureDate);


    if(arrivalValid && departureValid){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate arrival = LocalDate.parse(arrivalDate, formatter);
        LocalDate departure = LocalDate.parse(departureDate, formatter);

        // departure should either be after arrival or the same day
        if(!departure.isAfter(arrival) && !departure.isEqual(arrival)){
            throw new IllegalStateException("Arrival date must be before departure date.");
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
