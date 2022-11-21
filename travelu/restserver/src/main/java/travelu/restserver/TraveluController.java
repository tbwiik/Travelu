package travelu.restserver;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import travelu.core.Destination;
import travelu.core.DestinationList;

/**
 * Controller for Restserver
 * <p>
 * Handles requests
 * <p>
 * API-Address: {@code /api/v1/entries/}
 */
@RestController
@RequestMapping("/api/v1/entries/")
public class TraveluController {

    /**
     * Service used to handle local persistence
     */
    private final TraveluService traveluService = new TraveluService();

    /**
     * Get DestinationList
     * 
     * @return {@link DestinationList} in <i>JSON</i> format
     */
    @GetMapping(value = "destinationlist", produces = "application/json")
    public String getDestinationListJSON() {
        Gson gson = new Gson();
        return gson.toJson(traveluService.getDestinationList());
    }

    /**
     * Get destination by name
     * 
     * @param destinationName of Destination
     * @return {@link Destination} in <i>JSON</i> format
     * @throws NoSuchElementException if no element found
     */
    @GetMapping(value = "{destinationName}", produces = "application/json")
    public String getDestinationJSON(final @PathVariable("destinationName") String destinationName)
            throws NoSuchElementException {
        Gson gson = new Gson();
        Destination destination = traveluService.getDestinationList().getDestinationCopyByName(destinationName);
        return gson.toJson(destination);
    }

    /**
     * Get name of stored chosen destination
     * 
     * @return name of destination in string format
     */
    @GetMapping(value = "currentDestination", produces = "application/json")
    public String getDestinationJSON() {
        return traveluService.getDestinationName();
    }

    /**
     * Add new destination to list and save
     * 
     * @param destinationJSON {@link Destination} in <i>JSON</i> format
     * @throws IllegalArgumentException if already in list
     */
    @PostMapping(value = "add", produces = "application/json")
    public void addDestinationJSON(final @RequestBody String destinationJSON) throws IllegalArgumentException {
        Gson gson = new Gson();
        Destination destination = gson.fromJson(destinationJSON, Destination.class);
        traveluService.getDestinationList().addDestination(destination);
        traveluService.save();
    }

    /**
     * Add activity to current destination
     *
     * @param activity to add
     * @throws IllegalArgumentException if already in destination
     */
    @PostMapping(value = "addActivity", produces = "application/json")
    public void addActivityJSON(final @RequestBody String activity) throws IllegalArgumentException {

        Destination updatedDestination = getDestination();

        updatedDestination.addActivity(activity);

        updateDestination(updatedDestination);

    }

    /**
     * Store name of chosen destination
     * <p>
     * Accepts empty input
     * <p>
     * Converts "%20" to space
     * 
     * @param destinationName of destination
     */
    @PutMapping(value = "storeCurrent", produces = "application/json")
    public void storeCurrentDestinationJSON(final @RequestBody(required = false) String destinationNameJSON) {
        // Convert to empty string if empty comment is sent
        String destinationName = (destinationNameJSON == null) ? "" : destinationNameJSON.replaceAll("%20", " ");
        traveluService.saveDestinationName(destinationName);
    }

    /**
     * Set rating of current destination
     *
     * @param rating to set
     * @throws IllegalArgumentException if rating is outside of range 1-5
     */
    @PutMapping(value = "setRating", produces = "application/json")
    public void setRatingJSON(final @RequestBody String rating) throws IllegalArgumentException {

        Destination updatedDestination = getDestination();

        updatedDestination.setRating(Integer.parseInt(rating));

        updateDestination(updatedDestination);

    }

    /**
     * Set arrival date for current destination
     * 
     * @param arrivalDate to set
     * @throws IllegalArgumentException if invalid date or after departure
     */
    @PutMapping(value = "setArrivalDate", produces = "application/json")
    public void setArrivalDateJSON(final @RequestBody String arrivalDate) throws IllegalArgumentException {

        Destination updatedDestination = getDestination();

        updatedDestination.setArrivalDate(arrivalDate);

        updateDestination(updatedDestination);

    }

    /**
     * Set departure date for current destination
     *
     * @param departureDate to set
     * @throws IllegalArgumentException if invalid date or before arrival
     */
    @PutMapping(value = "setDepartureDate", produces = "application/json")
    public void setDepartureDateJSON(final @RequestBody String departureDate) throws IllegalArgumentException {

        Destination updatedDestination = getDestination();

        updatedDestination.setDepartureDate(departureDate);

        updateDestination(updatedDestination);

    }

    /**
     * Set new comment (overwrites) for current destination
     * <p>
     * Accepts empty input
     * 
     * @param comment to change to
     */
    @PutMapping(value = "updateComment", produces = "application/json")
    public void updateCommentJSON(final @RequestBody(required = false) String commentJSON) {

        // Convert to empty string if empty comment is sent
        String comment = (commentJSON == null) ? "" : commentJSON;

        Destination updatedDestination = getDestination();

        updatedDestination.setComment(comment);

        updateDestination(updatedDestination);

    }

    /**
     * Remove chosen destination
     * 
     * @param destinationName of destination to remove
     * @throws NoSuchElementException if destination not in list
     */
    @DeleteMapping(value = "delete/{destinationName}", produces = "application/json")
    public void removeDestinationJSON(final @PathVariable("destinationName") String destinationName)
            throws NoSuchElementException {

        traveluService.getDestinationList().removeDestination(destinationName.replace("%20", " "));

        traveluService.save();
    }

    /**
     * Remove activity from current destination
     * 
     * @param activity to remove
     * @throws NoSuchElementException if activity not in list
     */
    @DeleteMapping(value = "removeActivity/{activity}", produces = "application/json")
    public void removeActivityJSON(final @PathVariable String activity) throws NoSuchElementException {

        Destination updatedDestination = getDestination();

        updatedDestination.removeActivity(activity.replace("%20", " "));

        updateDestination(updatedDestination);

    }

    /**
     * Get a copy of the chosen destination
     * <p>
     * Helper-method for saving changes to file
     * 
     * @return currently chosen {@link Destination}
     */
    private Destination getDestination() {
        String destinationName = traveluService.getDestinationName();
        return traveluService.getDestinationList().getDestinationCopyByName(destinationName);
    }

    /**
     * Update currently chosen destination
     * <p>
     * Helper-method for saving changes to file
     * 
     * @param destination to update
     */
    private void updateDestination(Destination destination) {
        try {
            traveluService.getDestinationList().updateDestination(destination);
            traveluService.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exception thrown if failures in completing request with
     * <b>NoSuchElementException</b>
     * 
     * @param nsee automatically inserted by spring
     * @return string with error - sent over server
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String reportNoSuchElementException(final NoSuchElementException nsee) {
        return HttpStatus.NOT_FOUND + " - " + nsee.getMessage();
    }

    /**
     * Exception thrown if failures in completing request with
     * <b>IllegalArgumentException</b>
     * 
     * @param iae automatically inserted by spring
     * @return string with error - sent over server
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String reportIllegalArgumentException(final IllegalArgumentException iae) {
        return HttpStatus.BAD_REQUEST + " - " + iae.getMessage();
    }

}
