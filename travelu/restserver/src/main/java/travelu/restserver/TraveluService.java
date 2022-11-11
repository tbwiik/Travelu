package travelu.restserver;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Give access to destinationList used for persistence to other classes in
     * restserver module
     * 
     * @return this Destinationlist
     */
    protected DestinationList getDestinationList() {
        return this.destinationList;
    }
}
