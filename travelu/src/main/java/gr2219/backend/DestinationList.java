package gr2219.backend;

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

}
