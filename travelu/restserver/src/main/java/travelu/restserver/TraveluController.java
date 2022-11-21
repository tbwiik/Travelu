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

/**
 * Restserver controller
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
     * @return {@code DestinationList} in {@code JSON} format
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
     * @return Destination in string format
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
     * @return name of destination
     */
    @GetMapping(value = "currentDestination", produces = "application/json")
    public String getDestinationJSON() {
        return traveluService.getDestinationName();
    }

    /**
     * Add new Destination
     * 
     * @param destinationJSON {@link Destination} to add in JSON format
     */
    @PostMapping(value = "add", produces = "application/json")
    public void addDestinationJSON(final @RequestBody String destinationJSON) throws IllegalArgumentException {
        Gson gson = new Gson();
        Destination destination = gson.fromJson(destinationJSON, Destination.class);
        traveluService.getDestinationList().addDestination(destination);
        traveluService.save();
    }

    /**
     * add activity to current destination
     *
     * @param activity to add
     */
    @PostMapping(value = "addActivity", produces = "application/json")
    public void addActivityJSON(final @RequestBody String activity) throws IllegalArgumentException {

        Destination updatedDestination = getDestination();

        updatedDestination.addActivity(activity);

        updateDestination(updatedDestination);

    }

    /**
     * Store chosen destination
     * <p>
     * Accepts empty input
     * 
     * @param destinationName
     */
    @PutMapping(value = "storeCurrent", produces = "application/json")
    public void storeCurrentDestinationJSON(final @RequestBody(required = false) String destinationNameJSON) {
        // Convert to empty string if empty comment is sent
        String destinationName = (destinationNameJSON == null) ? "" : destinationNameJSON.replaceAll("%20", " ");

        traveluService.saveDestinationName(destinationName);
    }

    /**
     * set rating to current destination
     *
     * @param rating to set
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
     * @param arrivalDate
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
     */
    @PutMapping(value = "setDepartureDate", produces = "application/json")
    public void setDepartureDateJSON(final @RequestBody String departureDate) throws IllegalArgumentException {

        Destination updatedDestination = getDestination();

        updatedDestination.setDepartureDate(departureDate);

        updateDestination(updatedDestination);

    }

    /**
     * Set new comment for current destination
     * <p>
     * Accepts empty input
     * 
     * @param comment
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
     * @param destinationJSON
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
     */
    @DeleteMapping(value = "removeActivity/{activity}", produces = "application/json")
    public void removeActivityJSON(final @PathVariable String activity) throws NoSuchElementException {

        Destination updatedDestination = getDestination();

        updatedDestination.removeActivity(activity.replace("%20", " "));

        updateDestination(updatedDestination);

    }

    /**
     * Get a copy of the chosen destination
     * 
     * @return chosen destination
     */
    private Destination getDestination() {
        String destinationName = traveluService.getDestinationName();
        return traveluService.getDestinationList().getDestinationCopyByName(destinationName);
    }

    /**
     * Update chosen destination
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

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String reportNoSuchElementException(final NoSuchElementException nsee) {
        return HttpStatus.NOT_FOUND + " - " + nsee.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String reportIllegalArgumentException(final IllegalArgumentException iae) {
        return HttpStatus.BAD_REQUEST + " - " + iae.getMessage();
    }

}
