package travelu.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Date-interval, from arrivalDate to departureDate.
 * <p>
 * Stored as strings on format dd/MM/yyyy.
 * <p>
 * Dates are validated whenever they are changed.
 */
public class DateInterval {

    /**
     * Arrival date represented as string, on format dd/MM/yyyy.
     */
    private String arrivalDate;
    /**
     * Departure date represented as string, on format dd/MM/yyyy.
     */
    private String departureDate;

    /**
     * Create empty DateInterval.
     */
    public DateInterval() {
    }

    /**
     * Create copy of DateInterval.
     *
     * @param dateInterval object
     * @throws IllegalArgumentException if dateInterval is null
     */
    public DateInterval(final DateInterval dateInterval) throws IllegalArgumentException {
        if (dateInterval == null) {
            throw new IllegalArgumentException("dateInterval cannot be null");
        }

        this.arrivalDate = dateInterval.getArrivalDate();
        this.departureDate = dateInterval.getDepartureDate();
    }

    /**
     * @return arrivalDate - string on format dd/MM/yyyy.
     */
    public String getArrivalDate() {
        return this.arrivalDate;
    }

    /**
     * @return departureDate - string on format dd/MM/yyyy.
     */
    public String getDepartureDate() {
        return this.departureDate;
    }

    /**
     * Set arrival date.
     *
     * @param arrivalDate - string on format dd/MM/yyyy.
     * @throws IllegalArgumentException if arrivalDate is invalid.
     */
    public void setArrivalDate(final String arrivalDate) throws IllegalArgumentException {
        checkDatePair(arrivalDate, this.departureDate);
        this.arrivalDate = arrivalDate;
    }

    /**
     * Set departure date.
     *
     * @param departureDate - string on format dd/MM/yyyy
     * @throws IllegalArgumentException if departureDate is invalid
     */
    public void setDepartureDate(final String departureDate) throws IllegalArgumentException {
        checkDatePair(this.arrivalDate, departureDate);
        this.departureDate = departureDate;
    }

    /**
     * Checks that dateString represents a valid date with 3 parts:
     * day, month and year.
     * <p>
     * Uses inbuilt LocalDate validation.
     *
     * @param dateString on the format "dd/MM/yyyy". Formatting is strict, years
     *                   before 1000 and after 9999 are not accepted
     * @return true if date is valid
     */
    private boolean isValidDate(final String dateString) {
        try {
            // Split date into array of Strings, parse these as integers
            String[] dateArray = dateString.split("/");
            int day = Integer.parseInt(dateArray[0]);
            int month = Integer.parseInt(dateArray[1]);
            int year = Integer.parseInt(dateArray[2]);
            // This will throw an error if the date is not valid
            LocalDate.of(year, month, day);
            return dateArray.length == 3 && dateString.length() == 10;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether arrival and departure dates are valid.
     * Throws errors related to specific validity problems.
     *
     * @param arrivalDate   - string on format dd/MM/yyyy
     * @param departureDate - string on format dd/MM/yyyy
     * @throws IllegalArgumentException - If the date pair is invalid. Exception
     *                                  message describes the specific problem.
     */
    private void checkDatePair(final String arrivalDate, final String departureDate) throws IllegalArgumentException {

        boolean arrivalValid = isValidDate(arrivalDate);
        boolean departureValid = isValidDate(departureDate);

        if (arrivalValid && departureValid) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate arrival = LocalDate.parse(arrivalDate, formatter);
            LocalDate departure = LocalDate.parse(departureDate, formatter);

            // departure should either be after arrival or the same day
            if (!departure.isAfter(arrival) && !departure.isEqual(arrival)) {
                throw new IllegalStateException("Arrival date must be before departure date.");
            }

        } else if (!departureValid && departureDate != null) {
            throw new IllegalArgumentException("Invalid departure date.");
        } else if (!arrivalValid && arrivalDate != null) {
            throw new IllegalArgumentException("Invalid arrival date.");
        }
    }

}
