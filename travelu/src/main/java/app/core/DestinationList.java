package app.core;

import java.util.ArrayList;
import java.util.List;

public class DestinationList {

    private List<Destination> destinations = new ArrayList<>();

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public Destination getDestinationByName(String name) {
        return destinations.stream().filter(destination -> destination.getName() == name).findFirst().orElse(null);
    }

    public void removeDestination(String name) {

        Destination destination = getDestinationByName(name);
        if (destination == null) {
            throw new IllegalArgumentException("Invalid name");
        }

        destinations.remove(destination);
    }

    public List<Destination> getList() {
        return destinations;
    }

    public List<String> getDestinationNames(){
        List<String> destinationNames = new ArrayList<>();
        getList().forEach(destination -> destinationNames.add(destination.getName()));

        return destinationNames;
    }

    // Used to search for duplicates
    private List<String> getLowerCaseDestinationNames(){
        List<String> destinationNames = new ArrayList<>();
        getDestinationNames().forEach(destinationName -> destinationNames.add(destinationName.toLowerCase()));
        return destinationNames;
    }

    public boolean containsDestination(String destinationName){
        return getLowerCaseDestinationNames().contains(destinationName);
    }

}
