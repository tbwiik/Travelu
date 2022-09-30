package travelu.core;

import java.util.ArrayList;
import java.util.List;

/**
 * List of Destinations
 */
public class DestinationList {

    private List<Destination> destinations = new ArrayList<>();

    /**
     * Add destination to list
     * 
     * @param destination to add
     */
    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    /**
     * Get destination by name
     * 
     * @param name of destination
     * @return destination
     */
    public Destination getDestinationByName(String name) {
        return destinations.stream().filter(destination -> destination.getName() == name).findFirst().orElse(null);
    }

    /**
     * Remove destination by name
     * 
     * @param name of destination
     */
    public void removeDestination(String name) {

        Destination destination = getDestinationByName(name);
        if (destination == null) {
            throw new IllegalArgumentException("Invalid name");
        }

        destinations.remove(destination);
    }

    /**
     * Get list of destination objects
     * 
     * @return destination-list
     */
    public List<Destination> getList() {
        return new ArrayList<Destination>(destinations);
    }

    /**
     * Get list of destination names
     * 
     * @return name of destinations
     */
    public List<String> getDestinationNames() {
        List<String> destinationNames = new ArrayList<>();
        getList().forEach(destination -> destinationNames.add(destination.getName()));

        return destinationNames;
    }

    /**
     * Get list of destination names in lowercase, used to look for duplicates
     * 
     * @return list of names
     */
    private List<String> getLowerCaseDestinationNames() {
        List<String> destinationNames = new ArrayList<>();
        getDestinationNames().forEach(destinationName -> destinationNames.add(destinationName.toLowerCase()));
        return destinationNames;
    }

    /**
     * Checks if contains destination
     * 
     * @param destinationName
     * @return lowercase destination-name
     */
    public boolean containsDestination(String destinationName) {
        return getLowerCaseDestinationNames().contains(destinationName);
    }

}
