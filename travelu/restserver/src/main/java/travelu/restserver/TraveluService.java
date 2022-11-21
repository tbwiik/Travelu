package travelu.restserver;

import java.io.IOException;

import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;;

/**
 * Service handling persistence for rest-api
 */
public class TraveluService {

    /**
     * List of destinations
     */
    private DestinationList destinationList;

    /**
     * Creates a Service for the rest-controller
     * <p>
     * Empty list and name if failures
     */
    public TraveluService() {
        load();
    }

    /**
     * Load data from default file into this destinationList
     * <p>
     * Load empty list and name if failures
     */
    public void load() {
        try {
            this.destinationList = TraveluHandler.readDestinationListJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save data from this destinationList into default file
     */
    public void save() {
        try {
            TraveluHandler.save(destinationList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save current destination to default save-file
     */
    public void saveDestinationName(String currentDestination) {
        try {
            TraveluHandler.saveDestinationName(currentDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get currently chosen destination-name
     * <p>
     * Used to give server-controller access to loading/saving
     * 
     * @return name - empty string if none
     */
    public String getDestinationName() {
        try {
            return TraveluHandler.readCurrentDestinationNameJSON();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Get destination-list
     * <p>
     * Used to give server-controller access to loading/saving
     * 
     * @return {@link Destinationlist} - empty if failures
     */
    protected DestinationList getDestinationList() {
        return this.destinationList;
    }

}
