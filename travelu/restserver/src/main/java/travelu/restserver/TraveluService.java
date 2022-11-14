package travelu.restserver;

import java.io.IOException;

import travelu.core.DestinationList;
import travelu.localpersistence.TraveluHandler;;

/**
 * Give server access to other modules
 */
public class TraveluService {

    private DestinationList destinationList;

    /**
     * Creates a DestinationList on initialization containing data from file
     */
    public TraveluService() {
        load();
    }

    /**
     * Load data from default file into this destinationList
     */
    public void load() {
        try {
            this.destinationList = TraveluHandler.readDestinationListJSON();
        } catch (Exception e) {
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
     * Save current destination to own file
     */
    public void saveDestinationName(String currentDestination) {
        try {
            TraveluHandler.saveDestinationName(currentDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get name of chosen destination from file
     * 
     * @return {@link String} name of chosen destination or {@code null} if failing
     */
    public String getDestinationName() {
        String result = null;
        try {
            result = TraveluHandler.readCurrentDestinationNameJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Give access to destinationList used for persistence to other classes in
     * restserver module
     * 
     * @return this {@link Destinationlist}
     */
    protected DestinationList getDestinationList() {
        return this.destinationList; // TODO copy?
    }

}
