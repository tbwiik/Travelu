package travelu.core;

import java.util.ArrayList;
import java.util.List;

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
        this.dateInterval = dateInterval == null ? null : new DateInterval(dateInterval);
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
     */
    public Destination(Destination destination) {
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
        return null;
    }

    /**
     * Function used to set date-intervals with string input
     * <p>
     * Used by controller
     * 
     * @param startDate of stay on the form {@code d/m/y}
     * @param endDate   of stay on the form {@code d/m/y}
     * @throws IllegalArgumentException if lacking input on either startdate or
     *                                  enddate
     * @throws NumberFormatException    if input is wrong format
     */
    public void setDateInterval(String startDate, String endDate)
            throws IllegalArgumentException, NumberFormatException {

        if (startDate.isBlank() || endDate.isBlank()) {
            throw new IllegalArgumentException("Waiting for both dates to be set");
        }

        int[] startDateArray = { 0, 0, 0 };
        int i = 0;
        for (String dateComponent : startDate.toString().split("/")) {
            startDateArray[i] = Integer.parseInt(dateComponent);
            i++;
        }

        int[] endDateArray = { 0, 0, 0 };
        int j = 0;
        for (String dateComponent : endDate.toString().split("/")) {
            endDateArray[j] = Integer.parseInt(dateComponent);
            j++;
        }

        this.dateInterval = new DateInterval(startDateArray, endDateArray);

    }

    /**
     * @return the rating of the destination
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating on a scale of 1-5
     */
    public void setRating(int rating) {
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

        if (activity.isBlank() || getActivities().contains(activity))
            throw new IllegalArgumentException("Invalid activity");

        activities.add(activity);
    }

    /**
     * 
     * @param activity the activity we want to remove
     * @throws IllegalArgumentException if activity is not in list
     */
    public void removeActivity(String activity) throws IllegalArgumentException {
        if (!getActivities().contains(activity)) {
            throw new IllegalArgumentException("Activity is not in activity list");
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

    /**
     * Appends further comment
     * <p>
     * Starts on a new line
     * 
     * @param addComment comment to append
     */
    public void addComment(String addComment) {
        this.comment = this.comment + "\n" + addComment;
    }

    /**
     * Return true if names are equal
     * <p>
     * This is satisfactory because there will never be more than one object per
     * destination
     * <p>
     * Note: auto-generated stub
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Destination other = (Destination) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Hashes Destination according to name
     * <p>
     * Implemented to ensure safe use where hashing is needed
     * <p>
     * Note: Auto generated stub
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

}
