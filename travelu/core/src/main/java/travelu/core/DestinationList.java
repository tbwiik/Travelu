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
     * @throws IllegalArgumentException if destination is null
     */
    public void addDestination(Destination destination) {
        if (destination == null)
            throw new IllegalArgumentException("Destination cannot be null");

        destinations.add(destination);
    }

    /**
     * Get actual destination by name
     * @param name
     * @return destination
     */
    private Destination getDestinationByName(String name){
        return destinations.stream().filter(destination -> destination.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid name"));
    }

    /**
     * Get copy of destination by name
     * 
     * @param name of destination
     * @throws IllegalArgumentException if no destination with name
     * @return destination
     */
    public Destination getDestinationCopyByName(String name) {
        return new Destination(getDestinationByName(name));
    }

    /**
     * Remove destination by name
     * 
     * @param name of destination
     */
    public void removeDestination(String name) {
        Destination destination = getDestinationByName(name);
        destinations.remove(destination);
    }

    /**
     * updates destination. For use in DestinationController
     * @param destination to be updated
     */
    public void updateDestination(Destination destination){
        // Removes old version of destination from list, adds new version
        removeDestination(destination.getName());
        addDestination(destination);
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
     * @throws IllegalArgumentException if no destination with name
     * @return lowercase destination-name
     */
    public boolean containsDestination(String destinationName) {
        if (destinationName == null)
            throw new IllegalArgumentException("Destination name cannot be null");

        return getLowerCaseDestinationNames().contains(destinationName.toLowerCase());
    }

}
