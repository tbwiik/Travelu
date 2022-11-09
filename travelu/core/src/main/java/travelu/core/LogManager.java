package travelu.core;

/**
 * Entry for api and rest
 */
public class LogManager {

    private final DestinationList destinationList;

    public LogManager() {
        this.destinationList = new DestinationList();
    }

    public DestinationList getDestinationList() {
        return destinationList;
    }

}
