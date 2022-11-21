package travelu.core;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * List of Destinations
 */
public class DestinationList {

    private List<Destination> destinations = new ArrayList<>();

    /**
     * Add destination to list
     * 
     * @param destination to add
     * @throws IllegalArgumentException if destination is null, or if a destination with the same name already exists
     */
    public void addDestination(Destination destination) throws IllegalArgumentException {
        if (destination == null)
            throw new IllegalArgumentException("Destination cannot be null");

        // if list of destination names contains destination name, regardless of casing,
        // destination should not be added to destination list
        if (containsDestination(destination.getName()))
            throw new IllegalArgumentException("Destinationlist already contains " + destination.getName());

        destinations.add(new Destination(destination));
    }

    /**
     * Get actual destination by name
     * 
     * @param name of destination
     * @return {@link Destination}
     * @throws NoSuchElementException if no such element
     */
    private Destination getDestinationByName(String name) throws NoSuchElementException {
        return destinations.stream().filter(destination -> destination.getName().equals(name)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Destination " + name + " does not exist in list"));
    }

    /**
     * Get copy of destination by name
     * 
     * @param name of destination
     * @throws NoSuchElementException if no destination with name
     * @return destination
     */
    public Destination getDestinationCopyByName(String name) throws NoSuchElementException {
        return new Destination(getDestinationByName(name));
    }

    /**
     * Remove destination by name
     * 
     * @param name of destination
     * @throws IllegalArgumentException if name is null
     * @throws NoSuchElementException if no such element exist
     */
    public void removeDestination(String name) throws IllegalArgumentException, NoSuchElementException {
        // Name of destination to remove cannot be null
        if (name == null) {
            throw new IllegalArgumentException("Cannot remove null");
        }
        if (!this.containsDestination(name)) {
            throw new NoSuchElementException(name + " is not in destination list");
        }
        Destination destination = getDestinationByName(name);
        destinations.remove(destination);
    }

    /**
     * updates destination. For use in DestinationController
     * 
     * @param destination to be updated
     * @throws NoSuchElementException   if no such element exist
     * @throws IllegalArgumentException if inputing null-element
     */
    public void updateDestination(Destination destination) throws NoSuchElementException, IllegalArgumentException {
        // Removes old version of destination from list, adds new version
        if (destination == null)
            throw new IllegalArgumentException("Cannot update a non existing destination");
        removeDestination(destination.getName());
        addDestination(destination);
    }

    /**
     * Get list of copy of destination objects
     * 
     * @return destination-list
     */
    public List<Destination> getList() {
        List<Destination> copyList = new ArrayList<>();
        // Add copy of every destination in destinations to copyList
        destinations.forEach(destination -> copyList.add(new Destination(destination)));

        return copyList;
    }

    /**
     * Get list of destination names, used to display destinations to user
     * 
     * @return name of destinations
     */
    public List<String> getDestinationNames() {
        List<String> destinationNames = new ArrayList<>();
        getList().forEach(destination -> destinationNames.add(destination.getName()));

        return destinationNames;
    }

    /**
     * Get list of destination names in lowercase, used to look for duplicates in
     * containsDestination
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
     * @param destinationName name of destination to check for
     * @throws IllegalArgumentException if destinationName is null
     * @return boolean representing whether list contains destination or not
     */
    public boolean containsDestination(String destinationName) throws IllegalArgumentException {
        if (destinationName == null)
            throw new IllegalArgumentException("Destination name cannot be null");

        return getLowerCaseDestinationNames().contains(destinationName.toLowerCase());
    }

    /**
     * Sorts destinations by name
     */
    public void sortByName() {

        destinations.sort(
                (destination1, destination2) -> destination1.getName().toLowerCase()
                        .compareTo(destination2.getName().toLowerCase()));

    }

    /**
     * Sorts destinations by rating
     */
    public void sortByRating() {

        destinations.sort((destination1, destination2) -> destination2.getRating() - destination1.getRating());

    }

}
