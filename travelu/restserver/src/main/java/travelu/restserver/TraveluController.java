package travelu.restserver;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import travelu.core.Destination;

/**
 * Restserver controller
 */
@RestController
@RequestMapping("/api/v1/entries") // TODO rename??
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
    @GetMapping(value = "/destinationlist", produces = "application/json")
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
    @GetMapping(value = "/{destinationName}", produces = "application/json")
    public String getDestinationJSON(final @PathVariable("destinationName") String destinationName)
            throws NoSuchElementException {
        Gson gson = new Gson();
        return gson.toJson(traveluService.getDestinationList().getDestinationCopyByName(destinationName));
    }

    /**
     * Get name of chosen destination
     * 
     * @return name of destination
     */
    @GetMapping(value = "/currentDestination", produces = "application/json")
    public String getDestinationJSON() {
        Gson gson = new Gson();
        return gson.toJson(traveluService.getDestinationName());
    }

    /**
     * Add new Destination
     * 
     * @param destinationJSON {@link Destination} to add in JSON format
     */
    @PostMapping(value = "/add", produces = "application/json")
    public void addDestinationJSON(final @RequestBody String destinationJSON) {
        Gson gson = new Gson();
        try {
            Destination destination = gson.fromJson(destinationJSON, Destination.class);
            traveluService.getDestinationList().addDestination(destination);
            traveluService.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Store chosen destination
     * 
     * @param destinationName
     */
    @PostMapping(value = "/storeCurrent", produces = "application/json")
    public void storeCurrentDestinationJSON(final @RequestBody String destinationName) {
        Gson gson = new Gson();
        String destination = gson.fromJson(destinationName, String.class);
        traveluService.saveDestinationName(destination);
    }

    /**
     * Remove destination
     * 
     * @param destinationJSON
     */
    @PostMapping(value = "/remove", produces = "application/json")
    public void removeDestinationJSON(final @RequestBody String destinationName) {
        Gson gson = new Gson();
        String destination = gson.fromJson(destinationName, String.class);
        try {
            traveluService.getDestinationList().removeDestination(destination);
            traveluService.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * add activity to current destination
     *
     * @param activity to add
     */
    @PostMapping(value = "/addActivity", produces = "application/json")
    public void addActivityJSON(final @RequestBody String activity) {

        Destination updatedDestination = traveluService.getDestinationList()
                .getDestinationCopyByName(traveluService.getDestinationName());
        updatedDestination.addActivity(activity);

        try {
            traveluService.getDestinationList().updateDestination(updatedDestination);
            traveluService.save();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * remove activity from current destination
     * 
     * @param activity to remove
     */
    @PostMapping(value = "/removeActivity", produces = "application/json")
    public void removeActivityJSON(final @RequestBody String activity) {

        Destination updatedDestination = traveluService.getDestinationList()
                .getDestinationCopyByName(traveluService.getDestinationName());
        updatedDestination.removeActivity(activity);

        try {
            traveluService.getDestinationList().updateDestination(updatedDestination);
            traveluService.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO lot of duplicate code with over

    }

    /**
     * set rating to current destination
     *
     * @param rating to set
     */
    @PostMapping(value = "/setRating", produces = "application/json")
    public void setRatingJSON(final @RequestBody String rating) {

        Destination updatedDestination = traveluService.getDestinationList()
                .getDestinationCopyByName(traveluService.getDestinationName());
        updatedDestination.setRating(Integer.parseInt(rating));

        try {
            traveluService.getDestinationList().updateDestination(updatedDestination);
            traveluService.save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO lot of duplicate code with over

    }

}
