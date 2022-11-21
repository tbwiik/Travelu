package travelu.core;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Destination object for use in travel-journal
 */
public class Destination {

    private String name;
    private DateInterval dateInterval;
    private int rating;
    private List<String> activities;
    private String comment;

    /**
     * Constructs destination object
     * 
     * @param name       of destination
     * @param date       visit from-to
     * @param rating     on a scale from 1-5
     * @param activities you did during your visit
     * @param comment    with other relevant info
     */
    public Destination(String name, DateInterval dateInterval, int rating, List<String> activities,
            String comment) {
        this.name = name;

        // dateinterval is allowed to be null, but constructor should not take in null
        // as input
        this.dateInterval = dateInterval == null ? new DateInterval() : new DateInterval(dateInterval);
        this.rating = rating;

        // if activities are null, create new list. Otherwise create copy of old
        this.activities = activities == null ? new ArrayList<String>() : new ArrayList<String>(activities);

        this.comment = comment;
    }

    /**
     * Constructs destination object from already existing destination object,
     * creating a copy
     * 
     * @param destination
     * @throws IllegalArgumentException if destination is null
     */
    public Destination(Destination destination) throws IllegalArgumentException {
        if(destination == null){
            throw new IllegalArgumentException("Destination cannot be null");
        }

        this.name = destination.getName();
        this.dateInterval = destination.getDateInterval();
        this.rating = destination.getRating();
        this.activities = destination.getActivities();
        this.comment = destination.getComment();
    }

    /**
     * @return name of destination
     */
    public String getName() {
        return name;
    }

    /**
     * @return dateInterval from-to
     */
    public DateInterval getDateInterval() {
        if (dateInterval != null) {
            return new DateInterval(dateInterval);
        }
        return new DateInterval();
    }

    /**
     * Set arrival date for destination
     * 
     * @param arrivalDate - string in format dd/MM/yyyy
     * @throws IllegalArgumentException
     */
    public void setArrivalDate(String arrivalDate) throws IllegalArgumentException {
        dateInterval.setArrivalDate(arrivalDate);
    }

    /**
     * Set departure date for destination
     * 
     * @param departureDate - string in format dd/MM/yyyy
     * @throws IllegalArgumentException
     */
    public void setDepartureDate(String departureDate) throws IllegalArgumentException {
        dateInterval.setDepartureDate(departureDate);
    }

    /**
     * @return the rating of the destination
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating on a scale of 1-5
     * 
     * @throws IllegalArgumentException if <1 and >5
     */
    public void setRating(int rating) throws IllegalArgumentException {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    /**
     * @return copy of activities
     */
    public List<String> getActivities() {
        return new ArrayList<String>(activities);
    }

    /**
     * 
     * @param activity a string explaining the activity
     * @throws IllegalArgumentException if the input is blank
     */
    public void addActivity(String activity) throws IllegalArgumentException {

        if (activity == null || activity.isBlank() || getActivities().contains(activity))
            throw new IllegalArgumentException("Invalid activity");

        activities.add(activity);
    }

    /**
     * 
     * @param activity the activity we want to remove
     * @throws NoSuchElementException if activity is not in list
     */
    public void removeActivity(String activity) throws NoSuchElementException {
        if (!getActivities().contains(activity)) {
            throw new NoSuchElementException("Activity " + activity + " is not in activity list");
        }

        activities.remove(activity);
    }

    /**
     * @return additional comment about the visit
     */
    public String getComment() {
        return comment;
    }

    /**
     * Overwrites comment with new info
     * 
     * @param comment with other relevant info
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
